package ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.order.infrastructure.services.TextInvoiceFormatServiceAdapter;
import ca.ulaval.glo4003.trotti.payment.domain.services.PaymentService;
import ca.ulaval.glo4003.trotti.payment.domain.services.TransactionNotificationService;

public class OrderDomainServiceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadOrderDomainServices();
    }

    private void loadOrderDomainServices() {
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        InvoiceFormatService<String> invoiceFormatService = new TextInvoiceFormatServiceAdapter();
        this.resourceLocator.register(PaymentService.class, new PaymentService());
        this.resourceLocator.register(TextInvoiceFormatServiceAdapter.class,
                new TextInvoiceFormatServiceAdapter());
        this.resourceLocator.register(InvoiceNotificationService.class,
                new InvoiceNotificationService(emailService, invoiceFormatService));
        this.resourceLocator.register(TransactionNotificationService.class,
                new TransactionNotificationService(emailService));
    }

}
