package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationProvider;
import java.nio.file.Path;

public class TripRegistryLoader extends Bootstrapper {
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
