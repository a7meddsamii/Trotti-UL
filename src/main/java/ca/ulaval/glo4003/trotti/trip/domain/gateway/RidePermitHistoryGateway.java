package ca.ulaval.glo4003.trotti.trip.domain.gateway;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import java.util.List;

public interface RidePermitHistoryGateway {

    List<RidePermit> getFullHistory(Idul idul);

}
