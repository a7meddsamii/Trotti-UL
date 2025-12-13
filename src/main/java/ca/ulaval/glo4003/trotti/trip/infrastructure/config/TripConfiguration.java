package ca.ulaval.glo4003.trotti.trip.infrastructure.config;

import ca.ulaval.glo4003.trotti.config.Configuration;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders.*;

public class TripConfiguration extends Configuration {

    protected static Configuration instance;

    private TripConfiguration() {
        super();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new TripConfiguration();
        }

        return instance;
    }

    @Override
    protected void load() {
        new TripMapperLoader().load();
        new TripRepositoryLoader().load();
        new TripForeignServiceLoader().load();
        new TripApplicationServiceLoader().load();
        new TripResourceLoader().load();
    }
}
