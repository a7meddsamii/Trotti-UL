package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripCommandRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripQueryRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.filter.TripHistoryFilter;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryTripRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;


public class TripRepositoryLoader extends Bootstrapper {
    @Override
    public void load() {
        loadTripRepository();
    }

    private void loadTripRepository() {
        TripPersistenceMapper tripMapper =
                this.resourceLocator.resolve(TripPersistenceMapper.class);

        TripHistoryFilter tripHistoryFilter = new TripHistoryFilter();

        InMemoryTripRepository inMemoryTripRepository =
                new InMemoryTripRepository(tripMapper, tripHistoryFilter);

        this.resourceLocator.register(TripCommandRepository.class, inMemoryTripRepository);
        this.resourceLocator.register(TripQueryRepository.class, inMemoryTripRepository);
    }
}
