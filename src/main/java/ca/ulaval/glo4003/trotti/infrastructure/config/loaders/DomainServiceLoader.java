package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.SessionRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.EmailService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.services.TransactionNotificationService;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.domain.order.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.domain.order.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.communication.services.UnlockCodeNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.domain.trip.factories.StationFactory;
import ca.ulaval.glo4003.trotti.domain.trip.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.domain.trip.services.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.domain.trip.services.StationInitializationService;
import ca.ulaval.glo4003.trotti.domain.trip.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.domain.trip.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.infrastructure.order.services.TextInvoiceFormatServiceAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.trip.gateway.RidePermitHistoryGatewayAdapter;
import java.time.Clock;

public class DomainServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadOrderDomainServices();
        loadRidePermitActivationDomainServices();
        loadUnlockCodeDomainServices();
        loadStationInitializationService();
    }

    private void loadRidePermitActivationDomainServices() {
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        PassRepository passRepository = this.resourceLocator.resolve(PassRepository.class);
        EmployeeRegistry employeeRegistry = this.resourceLocator.resolve(EmployeeRegistry.class);
        SessionRegistry sessionRegistry = this.resourceLocator.resolve(SessionRegistry.class);

        this.resourceLocator.register(RidePermitNotificationService.class,
                new RidePermitNotificationService(emailService));
        this.resourceLocator.register(RidePermitHistoryGateway.class,
                new RidePermitHistoryGatewayAdapter(passRepository));
        this.resourceLocator.register(EmployeeRidePermitService.class,
                new EmployeeRidePermitService(employeeRegistry, sessionRegistry));
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
        Clock clock = this.resourceLocator.resolve(Clock.class);
        UnlockCodeService unlockCodeService = new UnlockCodeService(unlockCodeStore, clock);
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        UnlockCodeNotificationService unlockCodeNotificationService =
                new UnlockCodeNotificationService(emailService);
        this.resourceLocator.register(UnlockCodeService.class, unlockCodeService);
        this.resourceLocator.register(UnlockCodeNotificationService.class,
                unlockCodeNotificationService);
    }

    private void loadStationInitializationService() {
        StationFactory stationFactory = this.resourceLocator.resolve(StationFactory.class);
        ScooterFactory scooterFactory = this.resourceLocator.resolve(ScooterFactory.class);
        StationRepository stationRepository = this.resourceLocator.resolve(StationRepository.class);
        ScooterRepository scooterRepository = this.resourceLocator.resolve(ScooterRepository.class);

        StationInitializationService stationInitializationService =
                new StationInitializationService(stationFactory, scooterFactory, stationRepository,
                        scooterRepository);

        this.resourceLocator.register(StationInitializationService.class,
                stationInitializationService);
    }
}
