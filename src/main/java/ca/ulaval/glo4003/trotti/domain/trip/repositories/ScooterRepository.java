package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;

public interface ScooterRepository {

    void save(Scooter scooter);

    Scooter findById(ScooterId scooterId);
}
