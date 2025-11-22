package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TransferRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryScooterRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryStationRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryTransferRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryTripRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TransferPersistenceMapper;


public class TripRepositoryLoader extends Bootstrapper {
    @Override
    public void load() {
        loadTripRepository();
        loadStationRepository();
        loadScooterRepository();
        loadTransferRepository();
    }

    private void loadTripRepository() {
        TripPersistenceMapper tripMapper =
                this.resourceLocator.resolve(TripPersistenceMapper.class);

        TripRepository tripRepository = new InMemoryTripRepository(tripMapper);

        this.resourceLocator.register(TripRepository.class, tripRepository);
    }

    private void loadStationRepository() {
        StationPersistenceMapper stationPersistenceMapper =
                this.resourceLocator.resolve(StationPersistenceMapper.class);
        InMemoryStationRepository stationRepository =
                new InMemoryStationRepository(stationPersistenceMapper);
        this.resourceLocator.register(StationRepository.class, stationRepository);
    }

    private void loadScooterRepository() {
        ScooterPersistenceMapper scooterMapper =
                this.resourceLocator.resolve(ScooterPersistenceMapper.class);
        ScooterRepository scooterRepository = new InMemoryScooterRepository(scooterMapper);
        this.resourceLocator.register(ScooterRepository.class, scooterRepository);
    }

    private void loadTransferRepository() {
        TransferPersistenceMapper transferMapper =
                this.resourceLocator.resolve(TransferPersistenceMapper.class);
        TransferRepository transferRepository = new InMemoryTransferRepository(transferMapper);
        this.resourceLocator.register(TransferRepository.class, transferRepository);
    }
}
