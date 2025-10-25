package ca.ulaval.glo4003.trotti.infrastructure.config.datafactories;

import ca.ulaval.glo4003.trotti.domain.trip.services.StationInitializationService;
import ca.ulaval.glo4003.trotti.domain.trip.values.StationConfiguration;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.StationProvider;
import java.util.List;

public class StationDataFactory {
    private final StationInitializationService stationInitializationService;

    public StationDataFactory(StationInitializationService stationInitializationService) {
        this.stationInitializationService = stationInitializationService;
    }

    public void run() {
        List<StationConfiguration> stationConfigurations =
                StationProvider.getInstance().getStationConfigurations();
        stationInitializationService.initializeStations(stationConfigurations);
    }
}
