package ca.ulaval.glo4003.trotti.trip.domain.gateway;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

public interface RidePermitGateway {
    boolean isOwnerOfRidePermit(Idul idul, RidePermitId ridePermitId);
}
