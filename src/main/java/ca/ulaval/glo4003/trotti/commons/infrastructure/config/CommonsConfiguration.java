package ca.ulaval.glo4003.trotti.commons.infrastructure.config;

import ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders.DatabaseLoader;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders.EventBusLoader;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders.RegistryLoader;
import ca.ulaval.glo4003.trotti.config.Configuration;

public class CommonsConfiguration extends Configuration {

    protected static Configuration instance;

    private CommonsConfiguration() {
        super();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new CommonsConfiguration();
        }

        return instance;
    }

    @Override
    protected void load() {
        new EventBusLoader().load();
        new DatabaseLoader().load();
        new RegistryLoader().load();
    }
}
