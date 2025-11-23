package ca.ulaval.glo4003.trotti.order.domain.services;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.communication.domain.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.communication.domain.services.NotificationService;
import ca.ulaval.glo4003.trotti.communication.domain.values.EmailMessage;
import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.Invoice;

@Deprecated
public class InvoiceNotificationService implements NotificationService<Invoice> {
    private final EmailService emailService;
    private final InvoiceFormatService<String> invoiceFormatService;

    public InvoiceNotificationService(
            EmailService emailService,
            InvoiceFormatService<String> invoiceFormatService) {
        this.emailService = emailService;
        this.invoiceFormatService = invoiceFormatService;
    }

    @Override
    public void notify(Email recipient, Invoice content) {
        String formattedInvoice = invoiceFormatService.format(content);

        try {
            EmailMessage message = EmailMessage.builder().withRecipient(recipient)
                    .withSubject("Your Invoice").withBody(formattedInvoice).build();
            emailService.send(message);

        } catch (EmailSendException ignored) {
        }
    }
}
