package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.stations.StationProvider;
import java.nio.file.Path;

public class FleetRegistryLoader extends Bootstrapper {
    private static final Path STATION_DATA_FILE_PATH =
            Path.of("/app/data/campus-delivery-location.json");

    @Override
    public void load() {
        loadStationProvider();
    }

    private void loadStationProvider() {
        StationProvider.initialize(STATION_DATA_FILE_PATH);
        this.resourceLocator.register(StationProvider.class, StationProvider.getInstance());
    }
}
