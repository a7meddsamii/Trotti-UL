package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;

public interface ScooterRepository {

    void save(Scooter scooter);

    Scooter findById(ScooterId scooterId);
}
