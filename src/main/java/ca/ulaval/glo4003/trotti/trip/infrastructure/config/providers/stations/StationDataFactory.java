package ca.ulaval.glo4003.trotti.trip.infrastructure.config.providers.stations;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Station;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.factories.StationFactory;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.StationRepository;
import java.util.List;

public final class StationDataFactory {

    private final StationFactory stationFactory;
    private final ScooterFactory scooterFactory;
    private final StationRepository stationRepository;
    private final ScooterRepository scooterRepository;

    public StationDataFactory(
            StationFactory stationFactory,
            ScooterFactory scooterFactory,
            StationRepository stationRepository,
            ScooterRepository scooterRepository) {
        this.stationFactory = stationFactory;
        this.scooterFactory = scooterFactory;
        this.stationRepository = stationRepository;
        this.scooterRepository = scooterRepository;
    }

    public void run(List<StationDataRecord> stationDataRecords) {
        stationDataRecords.forEach(this::createAndPopulateStation);
    }

    /**
     * @deprecated correct the code in commented lines when application layer is added
     * 
     */
    private void createAndPopulateStation(StationDataRecord data) {
        Location location = Location.of(data.location(), data.name());
        Station station = stationFactory.create(location, data.capacity());

        int initialScooterCount = station.calculateInitialScooterCount();
        List<Scooter> scooters = scooterFactory.create(initialScooterCount, location);

        for (int i = 0; i < scooters.size(); i++) {
            Scooter scooter = scooters.get(i);
            scooterRepository.save(scooter);
            // station.returnScooter(new SlotNumber(i), scooter.getScooterId());
        }

        stationRepository.save(station);
    }
}
