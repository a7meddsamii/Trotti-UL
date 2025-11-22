package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import java.util.List;

public class MaintenanceRequestNotificationService {
    private final AccountRepository accountRepository;
    private final EmailService emailService;

    public MaintenanceRequestNotificationService(
            AccountRepository accountRepository,
            EmailService emailService) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
    }

    public void notifyTechnicians(String subject, String body) {
        List<Account> technicians = accountRepository.findByRole(Role.TECHNICIAN);

        for (Account technician : technicians) {
            EmailMessage message = EmailMessage.builder().withRecipient(technician.getEmail())
                    .withSubject(subject).withBody(body).build();

            emailService.send(message);
        }
    }
}
