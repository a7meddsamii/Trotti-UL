package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.commons.infrastructure.database.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AccountRepositoryLoader extends Bootstrapper {

    @Override
    public void load() {
        this.loadRepository();
    }

    private void loadRepository() {
        UserInMemoryDatabase userInMemoryDatabase =
                this.resourceLocator.resolve(UserInMemoryDatabase.class);
        AccountPersistenceMapper accountMapper =
                this.resourceLocator.resolve(AccountPersistenceMapper.class);
        AccountRepository accountRepository =
                new InMemoryAccountRepository(userInMemoryDatabase, accountMapper);
        this.resourceLocator.register(AccountRepository.class, accountRepository);
    }
}
