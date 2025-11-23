package ca.ulaval.glo4003.trotti.communication.infrastructure.config;

import ca.ulaval.glo4003.trotti.communication.infrastructure.config.loaders.CommunicationForeignServiceLoader;
import ca.ulaval.glo4003.trotti.communication.infrastructure.config.loaders.HandlerLoader;
import ca.ulaval.glo4003.trotti.communication.infrastructure.config.loaders.RepositoryLoader;
import ca.ulaval.glo4003.trotti.config.Configuration;

public class CommunicationConfiguration extends Configuration {

    protected static Configuration instance;

    private CommunicationConfiguration() {
        super();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new CommunicationConfiguration();
        }

        return instance;
    }

    @Override
    protected void load() {
        new RepositoryLoader().load();
        new CommunicationForeignServiceLoader().load();
        new HandlerLoader().load();
    }
}
