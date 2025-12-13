package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.StationFactory;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationDataFactory;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationDataRecord;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationProvider;
import java.time.Clock;
import java.util.List;

public class TripStationsLoader extends Bootstrapper {

    @Override
    public void load() {
        StationDataFactory stationDataFactory =
                new StationDataFactory(this.resourceLocator.resolve(StationFactory.class),
                        this.resourceLocator.resolve(ScooterFactory.class),
                        this.resourceLocator.resolve(StationRepository.class),
                        this.resourceLocator.resolve(ScooterRepository.class),
                        this.resourceLocator.resolve(Clock.class));
        List<StationDataRecord> stationData = StationProvider.getInstance().getStationDataRecords();
        stationDataFactory.run(stationData);
    }
}
