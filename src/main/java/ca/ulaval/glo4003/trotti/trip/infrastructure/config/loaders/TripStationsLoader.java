package ca.ulaval.glo4003.trotti.trip.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.ServerComponentLocator;
import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;
import ca.ulaval.glo4003.trotti.trip.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.trip.domain.factories.StationFactory;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationDataFactory;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationDataRecord;
import ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations.StationProvider;

import java.util.List;

public class TripStationsLoader extends Bootstrapper {
	
	@Override
	public void load() {
		ServerComponentLocator locator = ServerComponentLocator.getInstance();
		
		StationDataFactory stationDataFactory = new StationDataFactory(
				locator.resolve(StationFactory.class), locator.resolve(ScooterFactory.class),
				locator.resolve(StationRepository.class), locator.resolve(ScooterRepository.class));
		
		List<StationDataRecord> stationData = StationProvider.getInstance().getStationDataRecords();
		
		stationDataFactory.run(stationData);
	}
}
