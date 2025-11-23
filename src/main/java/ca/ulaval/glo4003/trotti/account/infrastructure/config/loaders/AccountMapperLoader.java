package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.infrastructure.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AccountMapperLoader extends Bootstrapper {

    @Override
    public void load() {
        loadAccountMapper();
    }

    private void loadAccountMapper() {
        PasswordHasher hasher = this.resourceLocator.resolve(PasswordHasher.class);
        this.resourceLocator.register(AccountApiMapper.class, new AccountApiMapper());
        this.resourceLocator.register(AccountPersistenceMapper.class,
                new AccountPersistenceMapper());
    }
}
