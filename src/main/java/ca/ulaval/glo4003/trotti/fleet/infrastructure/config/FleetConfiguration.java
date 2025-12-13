package ca.ulaval.glo4003.trotti.fleet.infrastructure.config;

import ca.ulaval.glo4003.trotti.config.Configuration;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders.*;

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
		new FleetRegistryLoader().load();
		new FleetMapperLoader().load();
		new FleetRepositoryLoader().load();
		new FleetFactoryLoader().load();
		new FleetStationsLoader().load();
		new FleetApplicationServiceLoader().load();
		new FleetGatewayEntryLoader().load();
    }
}
