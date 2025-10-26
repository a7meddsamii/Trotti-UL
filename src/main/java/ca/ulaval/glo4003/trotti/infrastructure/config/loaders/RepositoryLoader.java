package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.domain.account.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.infrastructure.account.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.PassPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.InMemoryBuyerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.InMemoryPassRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.inmemory.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.InMemoryScooterRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.InMemoryStationRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.InMemoryTravelerRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.InMemoryTripRepository;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TravelerRecord;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RepositoryLoader extends Bootstrapper {
    @Override
    public void load() {
        loadUserRepositories();
        loadPassRepository();
        loadTripRepository();
        loadStationRepository();
        loadScooterRepository();
    }

    private void loadUserRepositories() {
        ConcurrentMap<Idul, AccountRecord> accountTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, BuyerRecord> buyerTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, TravelerRecord> travelerTable = new ConcurrentHashMap<>();
        UserInMemoryDatabase userInMemoryDatabase =
                new UserInMemoryDatabase(accountTable, buyerTable, travelerTable);
        this.resourceLocator.register(UserInMemoryDatabase.class, userInMemoryDatabase);

        loadAccountRepository();
        loadBuyerRepository();
        loadTravelerRepository();
    }

    private void loadAccountRepository() {
        UserInMemoryDatabase userInMemoryDatabase =
                this.resourceLocator.resolve(UserInMemoryDatabase.class);
        AccountPersistenceMapper accountMapper =
                this.resourceLocator.resolve(AccountPersistenceMapper.class);

        AccountRepository accountRepository =
                new InMemoryAccountRepository(userInMemoryDatabase, accountMapper);
        this.resourceLocator.register(AccountRepository.class, accountRepository);
    }

    private void loadBuyerRepository() {
        UserInMemoryDatabase userInMemoryDatabase =
                this.resourceLocator.resolve(UserInMemoryDatabase.class);
        BuyerPersistenceMapper buyerMapper =
                this.resourceLocator.resolve(BuyerPersistenceMapper.class);

        BuyerRepository buyerRepository =
                new InMemoryBuyerRepository(userInMemoryDatabase, buyerMapper);
        this.resourceLocator.register(BuyerRepository.class, buyerRepository);
    }

    private void loadTravelerRepository() {
        UserInMemoryDatabase userInMemoryDatabase =
                this.resourceLocator.resolve(UserInMemoryDatabase.class);
        TravelerPersistenceMapper travelerMapper =
                this.resourceLocator.resolve(TravelerPersistenceMapper.class);

        TravelerRepository travelerRepository =
                new InMemoryTravelerRepository(userInMemoryDatabase, travelerMapper);
        this.resourceLocator.register(TravelerRepository.class, travelerRepository);
    }

    private void loadTripRepository() {
        TripPersistenceMapper tripMapper =
                this.resourceLocator.resolve(TripPersistenceMapper.class);

        TripRepository tripRepository = new InMemoryTripRepository(tripMapper);

        this.resourceLocator.register(TripRepository.class, tripRepository);
    }

    private void loadPassRepository() {
        PassPersistenceMapper passMapper =
                this.resourceLocator.resolve(PassPersistenceMapper.class);
        PassRepository passRepository = new InMemoryPassRepository(passMapper);
        this.resourceLocator.register(PassRepository.class, passRepository);
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
}
