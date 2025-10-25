package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.domain.account.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import ca.ulaval.glo4003.trotti.infrastructure.config.datafactories.AccountDevDataFactory;
import java.time.Clock;

public class ServerCompositionRoot {

    private static ServerCompositionRoot instance;
    private final ServerResourceLocator locator;
    private boolean resourcesCreated;

    public static ServerCompositionRoot getInstance() {
        if (instance == null) {
            instance = new ServerCompositionRoot();
        }

        return instance;
    }

    private ServerCompositionRoot() {
        this.locator = ServerResourceLocator.getInstance();
        this.resourcesCreated = false;
    }

    private void loadDevData() {
        AccountRepository accountRepository = locator.resolve(AccountRepository.class);
        PasswordHasher hasher = locator.resolve(PasswordHasher.class);
        new AccountDevDataFactory(accountRepository, hasher).run();
    }

    public void initiate() {
        if (resourcesCreated) {
            return;
        }

        this.locator.register(Clock.class, Clock.systemDefaultZone());
        new RegistryLoader().load();
        new ForeignServiceLoader().load();
        new MapperLoader().load();
        new RepositoryLoader().load();
        new FactoryLoader().load();
        new DomainServiceLoader().load();
        new ApplicationServiceLoader().load();
        new ApiEndPointLoader().load();

        loadDevData();
        resourcesCreated = true;
    }
}
