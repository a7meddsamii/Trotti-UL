package ca.ulaval.glo4003.trotti.trip.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.gatewayentry.RidePermitGatewayEntry;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;

public class RidePermitGatewayAdapter implements RidePermitGateway {
    private final RidePermitGatewayEntry ridePermitGatewayEntry;

    public RidePermitGatewayAdapter(RidePermitGatewayEntry ridePermitGatewayEntry) {
        this.ridePermitGatewayEntry = ridePermitGatewayEntry;
    }

    @Override
    public boolean isOwnerOfRidePermit(Idul idul, RidePermitId ridePermitId) {
        return ridePermitGatewayEntry.isRidePermitActive(idul, ridePermitId);
    }
}
