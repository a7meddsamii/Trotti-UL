package ca.ulaval.glo4003.trotti.billing.infrastructure.config;

import ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.BillingForeignServiceLoader;
import ca.ulaval.glo4003.trotti.config.Configuration;

public class BillingConfiguration extends Configuration {

    protected static Configuration instance;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new BillingConfiguration();
        }

        return instance;
    }

    private BillingConfiguration() {
        super();
    }

    @Override
    protected void load() {
        new BillingForeignServiceLoader().load();
    }
}
