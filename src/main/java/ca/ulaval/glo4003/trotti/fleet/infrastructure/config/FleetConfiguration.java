package ca.ulaval.glo4003.trotti.fleet.infrastructure.config;

import ca.ulaval.glo4003.trotti.config.Configuration;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders.FleetGatewayEntryLoader;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders.FleetStationsLoader;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders.FleetRegistryLoader;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders.*;

public class FleetConfiguration extends Configuration {

    protected static Configuration instance;

    private FleetConfiguration() {
        super();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new FleetConfiguration();
        }

        return instance;
    }

    @Override
    protected void load() {
        new TripMapperLoader().load();
        new TripRepositoryLoader().load();
        new FleetGatewayEntryLoader().load();
        new FleetRegistryLoader().load();
        new TripForeignServiceLoader().load();
        new TripFactoryLoader().load();
        new TripApplicationServiceLoader().load();
        new TripResourceLoader().load();
        new FleetStationsLoader().load();
    }
}
