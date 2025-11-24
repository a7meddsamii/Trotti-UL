package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory.RidePermitFactory;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class RidePermitFactoryLoader extends Bootstrapper {

    @Override
    public void load() {
        loadRidePermitFactory();
    }

    private void loadRidePermitFactory() {
        RidePermitFactory ridePermitFactory = new RidePermitFactory();
        this.resourceLocator.register(RidePermitFactory.class, ridePermitFactory);
    }
}
