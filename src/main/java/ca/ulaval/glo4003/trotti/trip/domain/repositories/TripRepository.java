package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripStatus;

import java.util.List;

public interface TripRepository {

    boolean exists(Idul idul, TripStatus status);

    void save(Trip trip);

    List<Trip> findAllByIdul(Idul idul);

    Trip findBy(Idul idul, TripStatus tripStatus);
}
