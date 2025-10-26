package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.ScooterRecord;
import java.util.HashMap;
import java.util.Map;

public class InMemoryScooterRepository implements ScooterRepository {

    private final ScooterPersistenceMapper scooterMapper;
    private Map<ScooterId, ScooterRecord> scooters = new HashMap<>();

    public InMemoryScooterRepository(ScooterPersistenceMapper scooterMapper) {
        this.scooterMapper = scooterMapper;
    }

    @Override
    public void save(Scooter scooter) {
        ScooterRecord record = scooterMapper.toRecord(scooter);
        scooters.put(scooter.getScooterId(), record);
    }

    @Override
    public Scooter findById(ScooterId scooterId) {
        return scooterMapper.toDomain(scooters.get(scooterId));

    }
}
