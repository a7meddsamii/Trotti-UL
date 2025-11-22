package ca.ulaval.glo4003.trotti.trip.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MaintenanceRequestNotificationServiceTest {
    private static final String SUBJECT = "Maintenance Request - Station A";
    private static final String BODY = "Docking station not working";

    private AccountRepository accountRepository;
    private EmailService emailService;
    private MaintenanceRequestNotificationService maintenanceRequestNotificationService;

    @BeforeEach
    void setup() {
        accountRepository = Mockito.mock(AccountRepository.class);
        emailService = Mockito.mock(EmailService.class);
        maintenanceRequestNotificationService =
                new MaintenanceRequestNotificationService(accountRepository, emailService);
    }

    @Test
    void givenTechnicians_whenNotifyTechnicians_thenSendEmailToAllTechnicians() {
        Account technician1 = Mockito.mock(Account.class);
        Account technician2 = Mockito.mock(Account.class);
        Email email1 = Email.from("tech1@ulaval.ca");
        Email email2 = Email.from("tech2@ulaval.ca");

        Mockito.when(technician1.getEmail()).thenReturn(email1);
        Mockito.when(technician2.getEmail()).thenReturn(email2);
        Mockito.when(accountRepository.findByRole(Role.TECHNICIAN))
                .thenReturn(List.of(technician1, technician2));

        maintenanceRequestNotificationService.notifyTechnicians(SUBJECT, BODY);

        Mockito.verify(emailService, Mockito.times(2)).send(Mockito.any(EmailMessage.class));
    }

    @Test
    void givenNoTechnicians_whenNotifyTechnicians_thenNoEmailsSent() {
        Mockito.when(accountRepository.findByRole(Role.TECHNICIAN))
                .thenReturn(Collections.emptyList());

        maintenanceRequestNotificationService.notifyTechnicians(SUBJECT, BODY);

        Mockito.verify(emailService, Mockito.never()).send(Mockito.any(EmailMessage.class));
    }
}
