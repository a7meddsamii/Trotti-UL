package ca.ulaval.glo4003.trotti.trip.domain.repositories;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import java.util.List;

public interface ScooterRepository {

    void save(Scooter scooter);

    Scooter findById(ScooterId scooterId);

    List<Scooter> findByIds(List<ScooterId> ids);

    void saveAll(List<Scooter> scooters);
}
