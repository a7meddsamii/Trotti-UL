package ca.ulaval.glo4003.trotti.domain.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import java.util.Optional;

public interface ScooterRepository {

    void save(Scooter scooter);

    Optional<Scooter> findById(Id scooterId);
}
