package ca.ulaval.glo4003.trotti.infrastructure.config.datafactories;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.domain.trip.factories.StationFactory;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.StationDataRecord;
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

    private void createAndPopulateStation(StationDataRecord data) {
        Location location = Location.of(data.location(), data.name());
        Station station = stationFactory.create(location, data.capacity());

        int initialScooterCount = station.calculateInitialScooterCount();
        List<Scooter> scooters = scooterFactory.create(initialScooterCount, location);

        for (int i = 0; i < scooters.size(); i++) {
            Scooter scooter = scooters.get(i);
            scooterRepository.save(scooter);
            station.returnScooter(new SlotNumber(i), scooter.getScooterId());
        }

        stationRepository.save(station);
    }
}
