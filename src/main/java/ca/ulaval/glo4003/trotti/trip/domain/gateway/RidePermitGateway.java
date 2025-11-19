package ca.ulaval.glo4003.trotti.trip.domain.gateway;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;

import java.util.List;

public interface RidePermitGateway {

    List<RidePermit> findAllByIdul(Idul idul);

    boolean isOwnerOfRidePermit(Idul idul, RidePermitId ridePermitId);
}
