package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.mappers.TripMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;

public class TripMapperLoader extends Bootstrapper {
    @Override
    public void load() {
        loadPersistenceMappers();
        loadApplicationMappers();
        loadApiMappers();
    }

    private void loadPersistenceMappers() {
        TripPersistenceMapper tripPersistenceMapper = new TripPersistenceMapper();
        this.resourceLocator.register(TripPersistenceMapper.class, tripPersistenceMapper);
    }

    private void loadApplicationMappers() {
        TripMapper tripMapper = new TripMapper();
        this.resourceLocator.register(TripMapper.class, tripMapper);
    }

    private void loadApiMappers() {
        this.resourceLocator.register(TripApiMapper.class, new TripApiMapper());
    }
}
