package ca.ulaval.glo4003.trotti.trip.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.gateway.RidePermitGateway;

public class RidePermitGatewayAdapter implements RidePermitGateway {

    @Override
    public boolean isOwnerOfRidePermit(Idul idul, RidePermitId ridePermitId) {
        // TODO to be implemented
        return true;
    }
}
