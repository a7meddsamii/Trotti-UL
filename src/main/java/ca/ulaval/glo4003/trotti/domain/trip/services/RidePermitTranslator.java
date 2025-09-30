package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.Traveler;

import java.time.LocalDate;
import java.util.List;

public interface RidePermitTranslator {

    List<RidePermit> findByIdul(Idul idul);

}
