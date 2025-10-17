package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.ScooterRecord;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryScooterRepository implements ScooterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryScooterRepository.class);

    private final Map<Id, ScooterRecord> scooters = new HashMap<>();
    private final ScooterPersistenceMapper scooterMapper;

    public InMemoryScooterRepository(ScooterPersistenceMapper scooterMapper) {
        this.scooterMapper = scooterMapper;
    }

    @Override
    public void save(Scooter scooter) {
        LOGGER.info("Saving scooter {}", scooter.getId());
        ScooterRecord record = scooterMapper.toRecord(scooter);
        scooters.put(scooter.getId(), record);
    }

    @Override
    public Scooter findById(Id scooterId) {
        ScooterRecord record = scooters.get(scooterId);
        return record != null ? scooterMapper.toDomain(record) : null;
    }
}
