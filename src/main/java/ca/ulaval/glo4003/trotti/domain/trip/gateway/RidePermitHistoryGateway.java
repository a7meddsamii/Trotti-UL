package ca.ulaval.glo4003.trotti.domain.trip.gateway;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import java.util.List;

public interface RidePermitHistoryGateway {

    List<RidePermit> getFullHistory(Idul idul);

}
