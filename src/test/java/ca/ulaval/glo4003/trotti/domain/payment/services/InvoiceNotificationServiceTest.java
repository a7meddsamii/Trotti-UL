package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.communication.values.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.services.EmailService;
import ca.ulaval.glo4003.trotti.domain.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.order.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InvoiceNotificationServiceTest {

    private static final Email AN_EMAIL = Email.from("abcdef@ulaval.ca");
    private static final String A_SUBJECT = "Your Invoice";
    private static final String A_FORMATTED_INVOICE = "Formatted Invoice";

    private EmailMessage emailMessage;
    private EmailService emailService;
    private InvoiceFormatService<String> invoiceFormatService;
    private NotificationService<Invoice> notificationService;

    @BeforeEach
    void setup() {
        emailMessage = Mockito.mock(EmailMessage.class);
        Mockito.when(emailMessage.getRecipient()).thenReturn(AN_EMAIL);
        Mockito.when(emailMessage.getSubject()).thenReturn("Your Invoice");
        emailService = Mockito.mock(EmailService.class);
        invoiceFormatService = Mockito.mock(InvoiceFormatService.class);
        notificationService = new InvoiceNotificationService(emailService, invoiceFormatService);
    }

    @Test
    void givenInvoice_whenNotify_thenEmailServiceIsCalled() {
        Invoice invoice = Mockito.mock(Invoice.class);
        Mockito.when(invoiceFormatService.format(invoice)).thenReturn(A_FORMATTED_INVOICE);

        notificationService.notify(AN_EMAIL, invoice);

        Mockito.verify(emailService)
                .send(Mockito.argThat(emailMessage -> emailMessage.getRecipient().equals(AN_EMAIL)
                        && emailMessage.getSubject().equals(A_SUBJECT)
                        && emailMessage.getBody().equals(A_FORMATTED_INVOICE)));
    }

    @Test
    void givenInvoice_whenNotify_thenEmailMessageIsCorrectlyFormatted() {
        Invoice invoice = Mockito.mock(Invoice.class);
        Mockito.when(invoiceFormatService.format(invoice)).thenReturn(A_FORMATTED_INVOICE);

        notificationService.notify(AN_EMAIL, invoice);

        Mockito.verify(invoiceFormatService).format(invoice);
    }
}
