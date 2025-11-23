package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.infrastructure.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepositoryLoader extends Bootstrapper {

    @Override
    public void load() {
        this.loadRepository();
    }

    private void loadRepository() {
        AccountPersistenceMapper accountMapper =
                this.resourceLocator.resolve(AccountPersistenceMapper.class);
        AccountRepository accountRepository =
                new InMemoryAccountRepository(new ConcurrentHashMap<>(), accountMapper);
        this.resourceLocator.register(AccountRepository.class, accountRepository);
    }
}
