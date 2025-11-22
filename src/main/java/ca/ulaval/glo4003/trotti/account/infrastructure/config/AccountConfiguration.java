package ca.ulaval.glo4003.trotti.account.infrastructure.config;

import ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders.*;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders.InitialAdminLoader;
import ca.ulaval.glo4003.trotti.config.Configuration;

public class AccountConfiguration extends Configuration {

    protected static Configuration instance;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new AccountConfiguration();
        }
        return instance;
    }

    @Override
    protected void load() {
        new AccountForeignServiceLoader().load();
        new AccountMapperLoader().load();
        new AccountRepositoryLoader().load();
        new AccountFactoryLoader().load();
        new AccountApplicationLoader().load();
        new AccountResourceLoader().load();
        new InitialAdminLoader().load();
    }
}
