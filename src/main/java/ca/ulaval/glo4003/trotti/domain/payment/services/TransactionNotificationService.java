package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.communication.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.domain.communication.services.EmailService;
import ca.ulaval.glo4003.trotti.domain.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.communication.values.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.payment.values.transaction.Transaction;

public class TransactionNotificationService implements NotificationService<Transaction> {
    private final EmailService emailService;

    public TransactionNotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notify(Email recipient, Transaction transaction) {
        try {
            EmailMessage message = EmailMessage.builder().withRecipient(recipient)
                    .withSubject("Transaction Details").withBody(transaction.toString()).build();
            emailService.send(message);

        } catch (EmailSendException ignored) {
        }
    }
}
