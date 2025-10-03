package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.EmailSendException;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import ca.ulaval.glo4003.trotti.domain.order.Invoice;

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
