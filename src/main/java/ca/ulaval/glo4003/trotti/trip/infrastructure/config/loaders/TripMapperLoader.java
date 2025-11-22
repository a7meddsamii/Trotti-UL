package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;

public class TripMapperLoader extends Bootstrapper {
    @Override
    public void load() {
        loadPersistenceMappers();
        loadApiMappers();
    }

    private void loadPersistenceMappers() {
        TripPersistenceMapper tripPersistenceMapper = new TripPersistenceMapper();
        this.resourceLocator.register(TripPersistenceMapper.class, tripPersistenceMapper);
        this.resourceLocator.register(ScooterPersistenceMapper.class, new ScooterPersistenceMapper());
        this.resourceLocator.register(StationPersistenceMapper.class,
                new StationPersistenceMapper());
    }

    private void loadApiMappers() {
        this.resourceLocator.register(TripApiMapper.class, new TripApiMapper());
    }
}
