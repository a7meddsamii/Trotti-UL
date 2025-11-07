package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.infrastructure.database.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TravelerRecord;
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
        return databaseDriver.getAllTravelers().stream().map(travelerMapper::toDomain).toList();
    }

    @Override
    public Traveler findByIdul(Idul idul) {
        TravelerRecord travelerRecord = databaseDriver.selectFromTravelerTable(idul);
        return travelerMapper.toDomain(travelerRecord);
    }

    @Override
    public void update(Traveler traveler) {
        TravelerRecord travelerRecord = travelerMapper.toRecord(traveler);
        databaseDriver.insertIntoTravelerTable(travelerRecord);
    }
}
