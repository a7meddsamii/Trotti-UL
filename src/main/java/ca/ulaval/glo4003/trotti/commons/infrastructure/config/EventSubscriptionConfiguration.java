package ca.ulaval.glo4003.trotti.commons.infrastructure.config;

import ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders.EventSubscriptionLoader;
import ca.ulaval.glo4003.trotti.config.Configuration;

public class EventSubscriptionConfiguration extends Configuration {

    protected static Configuration instance;

    private EventSubscriptionConfiguration() {
        super();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new EventSubscriptionConfiguration();
        }

        return instance;
    }

    @Override
    protected void load() {
        new EventSubscriptionLoader().load();
    }
}
