package ca.ulaval.glo4003.trotti.billing.api.ridepermit.gatewayentry;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

public class RidePermitGatewayEntry {
    private final RidePermitApplicationService ridePermitApplicationService;

    public RidePermitGatewayEntry(RidePermitApplicationService ridePermitApplicationService) {
        this.ridePermitApplicationService = ridePermitApplicationService;
    }

    public boolean isRidePermitActive(Idul riderId, RidePermitId ridePermitId) {
        return ridePermitApplicationService.isRidePermitActive(riderId, ridePermitId);
    }
}
