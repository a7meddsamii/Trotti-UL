package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;

public interface TripRepository {

    void save(Trip trip);
}
