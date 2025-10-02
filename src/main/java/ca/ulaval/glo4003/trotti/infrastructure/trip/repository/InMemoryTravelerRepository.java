package ca.ulaval.glo4003.trotti.infrastructure.trip.repository;

import ca.ulaval.glo4003.trotti.domain.trip.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.repository.TravelerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.records.TravelerRecord;
import java.util.List;

public class InMemoryTravelerRepository implements TravelerRepository {
    private final UserInMemoryDatabase databaseDriver;
    private final TravelerPersistenceMapper travelerMapper;

    public InMemoryTravelerRepository(
            UserInMemoryDatabase databaseDriver,
            TravelerPersistenceMapper travelerMapper) {
        this.databaseDriver = databaseDriver;
        this.travelerMapper = travelerMapper;
    }

    @Override
    public List<Traveler> findAll() {

        return databaseDriver.getAllTravelers().stream()
                .map(travelerMapper::toDomain)
                .toList();
    }

    @Override
    public void update(Traveler traveler) {
        TravelerRecord travelerRecord = travelerMapper.toRecord(traveler);
        databaseDriver.insertIntoTravelerTable(travelerRecord);
    }
}
