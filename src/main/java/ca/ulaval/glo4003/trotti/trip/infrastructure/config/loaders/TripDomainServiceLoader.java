package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.gateways.EmployeeRegistryGateway;
import ca.ulaval.glo4003.trotti.commons.domain.gateways.SchoolSessionGateway;
import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitHistoryGateway;
import ca.ulaval.glo4003.trotti.trip.domain.services.EmployeeRidePermitService;
import ca.ulaval.glo4003.trotti.trip.domain.services.RidePermitNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.infrastructure.gateway.RidePermitHistoryGatewayAdapter;

public class TripDomainServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadRidePermitActivationDomainServices();
        loadUnlockCodeDomainServices();
    }

    private void loadRidePermitActivationDomainServices() {
        EmailService emailService = this.resourceLocator.resolve(EmailService.class);
        PassRepository passRepository = this.resourceLocator.resolve(PassRepository.class);
        EmployeeRegistryGateway employeeRegistryGateway =
                this.resourceLocator.resolve(EmployeeRegistryGateway.class);
        SchoolSessionGateway schoolSessionGateway =
                this.resourceLocator.resolve(SchoolSessionGateway.class);

        this.resourceLocator.register(RidePermitNotificationService.class,
                new RidePermitNotificationService(emailService));
        this.resourceLocator.register(RidePermitHistoryGateway.class,
                new RidePermitHistoryGatewayAdapter(passRepository));
        this.resourceLocator.register(EmployeeRidePermitService.class,
                new EmployeeRidePermitService(employeeRegistryGateway, schoolSessionGateway));
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
