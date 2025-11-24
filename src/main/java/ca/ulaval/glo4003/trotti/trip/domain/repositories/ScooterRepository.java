package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.util.List;

public interface ScooterRepository {

    void save(Scooter scooter);

    Scooter findById(ScooterId scooterId);

    List<Scooter> findByIds(List<ScooterId> ids);

    void saveAll(List<Scooter> scooters);
}
