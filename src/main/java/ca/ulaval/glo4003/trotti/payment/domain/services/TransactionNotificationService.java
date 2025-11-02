package ca.ulaval.glo4003.trotti.payment.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.payment.domain.values.transaction.Transaction;

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
