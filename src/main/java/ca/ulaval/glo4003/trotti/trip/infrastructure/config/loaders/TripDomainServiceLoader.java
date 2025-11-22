package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.communication.domain.services.EmailService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.ScooterRentalGateway;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeNotificationService;
import ca.ulaval.glo4003.trotti.trip.domain.services.UnlockCodeService;
import ca.ulaval.glo4003.trotti.trip.domain.store.UnlockCodeStore;
import ca.ulaval.glo4003.trotti.trip.infrastructure.gateway.ScooterRentalGatewayAdapter;

public class TripDomainServiceLoader extends Bootstrapper {
    @Override
    public void load() {
        loadUnlockCodeDomainServices();
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
}
