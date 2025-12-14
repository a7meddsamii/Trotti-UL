package ca.ulaval.glo4003.trotti.fleet.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.StationFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.stations.FleetDataFactory;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.stations.StationDataRecord;
import ca.ulaval.glo4003.trotti.fleet.infrastructure.config.providers.stations.StationProvider;
import java.time.Clock;
import java.util.List;

public class FleetStationsLoader extends Bootstrapper {

    @Override
    public void load() {
        FleetDataFactory fleetDataFactory =
                new FleetDataFactory(this.resourceLocator.resolve(StationFactory.class),
                        this.resourceLocator.resolve(ScooterFactory.class),
                        this.resourceLocator.resolve(FleetRepository.class),
                        this.resourceLocator.resolve(Clock.class));
        List<StationDataRecord> stationData = StationProvider.getInstance().getStationDataRecords();
        fleetDataFactory.run(stationData);
    }
}
