package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransactionNotificationServiceTest {

    private static final Email AN_EMAIL = Email.from("abcdef@ulaval.ca");
    private static final String A_SUBJECT = "Transaction Details";
    private static final String TRANSACTION_DETAILS = "ABCDTest";

    private EmailMessage emailMessage;
    private EmailService emailService;
    private NotificationService<Transaction> transactionNotificationService;

    @BeforeEach
    void setup() {
        emailMessage = Mockito.mock(EmailMessage.class);
        Mockito.when(emailMessage.getRecipient()).thenReturn(AN_EMAIL);
        Mockito.when(emailMessage.getSubject()).thenReturn("Your Invoice");
        emailService = Mockito.mock(EmailService.class);
        transactionNotificationService = new TransactionNotificationService(emailService);
    }

    @Test
    void givenInvoice_whenNotify_thenEmailServiceIsCalled() {
        Transaction transaction = Mockito.mock(Transaction.class);
        Mockito.when(transaction.toString()).thenReturn(TRANSACTION_DETAILS);

        transactionNotificationService.notify(AN_EMAIL, transaction);

        Mockito.verify(emailService)
                .send(Mockito.argThat(emailMessage -> emailMessage.getRecipient().equals(AN_EMAIL)
                        && emailMessage.getSubject().equals(A_SUBJECT)
                        && emailMessage.getBody().equals(TRANSACTION_DETAILS)));
    }
}
