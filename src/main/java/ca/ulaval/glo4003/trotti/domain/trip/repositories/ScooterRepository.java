package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;

public interface ScooterRepository {

    void save(Scooter scooter);

    Scooter findById(String scooterId);
}
