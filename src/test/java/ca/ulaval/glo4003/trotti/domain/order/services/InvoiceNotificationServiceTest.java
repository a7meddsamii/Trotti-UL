package ca.ulaval.glo4003.trotti.domain.order.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.order.domain.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceNotificationService;
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
