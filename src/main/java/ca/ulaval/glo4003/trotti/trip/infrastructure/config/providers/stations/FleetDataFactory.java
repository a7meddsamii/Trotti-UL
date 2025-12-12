package ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Fleet;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.StationFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.repositories.FleetRepository;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FleetDataFactory {

    private final StationFactory stationFactory;
    private final ScooterFactory scooterFactory;
	private final FleetRepository fleetRepository;
    private final Clock clock;

    public FleetDataFactory(
			StationFactory stationFactory,
			ScooterFactory scooterFactory,
			FleetRepository fleetRepository,
			Clock clock) {
        this.stationFactory = stationFactory;
        this.scooterFactory = scooterFactory;
		this.fleetRepository = fleetRepository;
        this.clock = clock;
    }

    public void run(List<StationDataRecord> stationDataRecords) {
		Map<Location, Station> stations = new HashMap<>();
		
		for (StationDataRecord data : stationDataRecords) {
			Station station = createAndPopulateStation(data);
			stations.put(station.getLocation(), station);
		}
		
		Fleet fleet = new Fleet(stations, new HashMap<>());
		fleetRepository.save(fleet);
    }

    private Station createAndPopulateStation(StationDataRecord data) {
        Location location = Location.of(data.location(), data.name());
        Station station = stationFactory.create(location, data.capacity());

        int initialScooterCount = station.calculateInitialScooterCount();
        List<Scooter> scooters = scooterFactory.create(initialScooterCount, location);
        LocalDateTime dockingTime = LocalDateTime.now(clock);

        for (int i = 0; i < scooters.size(); i++) {
            Scooter scooter = scooters.get(i);
            station.parkScooter(new SlotNumber(i), scooter, dockingTime);
        }

        return station;
    }
}
