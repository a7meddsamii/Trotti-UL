package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.ridepermit;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.gatewayentry.RidePermitGatewayEntry;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class RidePermitGatewayEntryLoader extends Bootstrapper {

    @Override
    public void load() {
        loadRidePermitGatewayEntry();
    }

    private void loadRidePermitGatewayEntry() {
        RidePermitApplicationService ridePermitApplicationService =
                this.resourceLocator.resolve(RidePermitApplicationService.class);
        RidePermitGatewayEntry ridePermitGatewayEntry =
                new RidePermitGatewayEntry(ridePermitApplicationService);
        this.resourceLocator.register(RidePermitGatewayEntry.class, ridePermitGatewayEntry);
    }
}
