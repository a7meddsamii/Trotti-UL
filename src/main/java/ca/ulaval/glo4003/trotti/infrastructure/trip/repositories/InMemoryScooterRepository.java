package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.ScooterRecord;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryScooterRepository implements ScooterRepository {

    private final Map<Id, ScooterRecord> scooters = new HashMap<>();
    private final ScooterPersistenceMapper scooterMapper;

    public InMemoryScooterRepository(ScooterPersistenceMapper scooterMapper) {
        this.scooterMapper = scooterMapper;
    }

    @Override
    public void save(Scooter scooter) {
        ScooterRecord record = scooterMapper.toRecord(scooter);
        scooters.put(scooter.getId(), record);
    }

    @Override
    public Optional<Scooter> findById(Id scooterId) {

        return Optional.ofNullable(scooters.get(scooterId)).map(scooterMapper::toDomain);
    }
}
