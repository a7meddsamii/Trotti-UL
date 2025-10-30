package ca.ulaval.glo4003.trotti.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripRepository;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.mappers.PassPersistenceMapper;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.InMemoryBuyerRepository;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.InMemoryPassRepository;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.commons.infrastructure.database.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.StationPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TripPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryScooterRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryStationRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryTravelerRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryTripRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TravelerRecord;
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
