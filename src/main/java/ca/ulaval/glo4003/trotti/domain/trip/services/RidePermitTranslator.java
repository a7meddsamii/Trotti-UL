package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import java.util.List;

public interface RidePermitTranslator {

    List<RidePermit> findByIdul(Idul idul);

}
