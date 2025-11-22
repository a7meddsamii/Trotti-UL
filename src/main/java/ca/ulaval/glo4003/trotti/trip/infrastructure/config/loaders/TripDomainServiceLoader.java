package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.config.events.InMemoryEventBus;
import ca.ulaval.glo4003.trotti.config.events.handlers.MaintenanceRequestedEventHandler;
import ca.ulaval.glo4003.trotti.order.domain.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.trip.domain.events.MaintenanceRequestedEvent;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.trip.domain.services.MaintenanceRequestNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.infrastructure.gateway.RidePermitGatewayAdapter;
import ca.ulaval.glo4003.trotti.trip.infrastructure.gateway.ScooterRentalGatewayAdapter;

public class TripDomainServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadRidePermitActivationDomainServices();
        loadUnlockCodeDomainServices();
        loadMaintenanceRequestDomainServices();
    }

    private void loadRidePermitActivationDomainServices() {
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        PassRepository passRepository = this.resourceLocator.resolve(PassRepository.class);
        // EmployeeRegistryProvider employeeRegistryProvider =
        // this.resourceLocator.resolve(EmployeeRegistryProvider.class);
        SchoolSessionProvider schoolSessionProvider =
                this.resourceLocator.resolve(SchoolSessionProvider.class);

        this.resourceLocator.register(RidePermitNotificationService.class,
                new RidePermitNotificationService(emailService));
        this.resourceLocator.register(RidePermitGateway.class,
                new RidePermitGatewayAdapter(passRepository));
        this.resourceLocator.register(EmployeeRidePermitService.class,
                new EmployeeRidePermitService(null, schoolSessionProvider));
    }

    private void loadUnlockCodeDomainServices() {
        UnlockCodeStore unlockCodeStore = this.resourceLocator.resolve(UnlockCodeStore.class);
        UnlockCodeService unlockCodeService = new UnlockCodeService(unlockCodeStore);
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        ScooterRentalGateway scooterRentalGateway = new ScooterRentalGatewayAdapter();
        this.resourceLocator.register(ScooterRentalGateway.class, scooterRentalGateway);
        UnlockCodeNotificationService unlockCodeNotificationService =
                new UnlockCodeNotificationService(emailService);
        this.resourceLocator.register(UnlockCodeService.class, unlockCodeService);
        this.resourceLocator.register(UnlockCodeNotificationService.class,
                unlockCodeNotificationService);
    }

    private void loadMaintenanceRequestDomainServices() {
        AccountRepository accountRepository = this.resourceLocator.resolve(AccountRepository.class);
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);

        MaintenanceRequestNotificationService maintenanceRequestNotificationService =
                new MaintenanceRequestNotificationService(accountRepository, emailService);
        this.resourceLocator.register(MaintenanceRequestNotificationService.class,
                maintenanceRequestNotificationService);

        MaintenanceRequestedEventHandler handler =
                new MaintenanceRequestedEventHandler(maintenanceRequestNotificationService);
        ((InMemoryEventBus) eventBus).subscribe(MaintenanceRequestedEvent.class, handler);
    }
}
