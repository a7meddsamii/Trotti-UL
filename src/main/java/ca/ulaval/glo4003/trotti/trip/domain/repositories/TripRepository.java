package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;

import java.util.List;

public interface TripRepository {

    void save(Trip trip);

    List<Trip> findAllByIdul(Idul idul);
}
