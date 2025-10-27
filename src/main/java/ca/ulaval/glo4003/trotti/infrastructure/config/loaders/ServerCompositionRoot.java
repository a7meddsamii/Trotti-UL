package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.domain.account.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.trip.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.domain.trip.factories.StationFactory;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationDataRecord;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerComponentLocator;
import ca.ulaval.glo4003.trotti.infrastructure.config.datafactories.AccountDevDataFactory;
import ca.ulaval.glo4003.trotti.infrastructure.config.datafactories.StationDataFactory;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.StationProvider;
import java.time.Clock;
import java.util.List;

public class ServerCompositionRoot {

    private static ServerCompositionRoot instance;
    private boolean componentsCreated;

    public static ServerCompositionRoot getInstance() {
        if (instance == null) {
            instance = new ServerCompositionRoot();
        }

        return instance;
    }

    private ServerCompositionRoot() {
        this.componentsCreated = false;
    }

    public void initiate() {
        if (componentsCreated) {
            return;
        }

        ServerComponentLocator locator = ServerComponentLocator.getInstance();
        locator.register(Clock.class, Clock.systemDefaultZone());
        new RegistryLoader().load();
        new ForeignServiceLoader().load();
        new MapperLoader().load();
        new RepositoryLoader().load();
        new FactoryLoader().load();
        new DomainServiceLoader().load();
        new ApplicationServiceLoader().load();
        new ResourceLoader().load();

        loadDevData();
        loadStations();
        componentsCreated = true;
    }

    private void loadDevData() {
        ServerComponentLocator locator = ServerComponentLocator.getInstance();
        AccountRepository accountRepository = locator.resolve(AccountRepository.class);
        PasswordHasher hasher = locator.resolve(PasswordHasher.class);
        new AccountDevDataFactory(accountRepository, hasher).run();
    }

    private void loadStations() {
        ServerComponentLocator locator = ServerComponentLocator.getInstance();

        StationDataFactory stationDataFactory = new StationDataFactory(
                locator.resolve(StationFactory.class), locator.resolve(ScooterFactory.class),
                locator.resolve(StationRepository.class), locator.resolve(ScooterRepository.class));

        List<StationDataRecord> stationData = StationProvider.getInstance().getStationDataRecords();

        stationDataFactory.run(stationData);
    }
}
