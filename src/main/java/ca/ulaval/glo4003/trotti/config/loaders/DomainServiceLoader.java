package ca.ulaval.glo4003.trotti.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.commons.domain.SessionEnum;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.order.infrastructure.services.TextInvoiceFormatServiceAdapter;
import ca.ulaval.glo4003.trotti.payment.domain.services.PaymentService;
import ca.ulaval.glo4003.trotti.payment.domain.services.TransactionNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.trip.domain.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.trip.domain.services.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.infrastructure.gateway.RidePermitHistoryGatewayAdapter;

public class DomainServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadOrderDomainServices();
        loadRidePermitActivationDomainServices();
        loadUnlockCodeDomainServices();
    }

    private void loadRidePermitActivationDomainServices() {
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        PassRepository passRepository = this.resourceLocator.resolve(PassRepository.class);
        EmployeeRegistry employeeRegistry = this.resourceLocator.resolve(EmployeeRegistry.class);
        SessionEnum sessionEnum = this.resourceLocator.resolve(SessionEnum.class);

        this.resourceLocator.register(RidePermitNotificationService.class,
                new RidePermitNotificationService(emailService));
        this.resourceLocator.register(RidePermitHistoryGateway.class,
                new RidePermitHistoryGatewayAdapter(passRepository));
        this.resourceLocator.register(EmployeeRidePermitService.class,
                new EmployeeRidePermitService(employeeRegistry, sessionEnum));
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

    private void loadUnlockCodeDomainServices() {
        UnlockCodeStore unlockCodeStore = this.resourceLocator.resolve(UnlockCodeStore.class);
        UnlockCodeService unlockCodeService = new UnlockCodeService(unlockCodeStore);
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        UnlockCodeNotificationService unlockCodeNotificationService =
                new UnlockCodeNotificationService(emailService);
        this.resourceLocator.register(UnlockCodeService.class, unlockCodeService);
        this.resourceLocator.register(UnlockCodeNotificationService.class,
                unlockCodeNotificationService);
    }
}
