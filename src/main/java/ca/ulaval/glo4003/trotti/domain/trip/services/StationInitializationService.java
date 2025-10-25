package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.domain.trip.factories.StationFactory;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import ca.ulaval.glo4003.trotti.domain.trip.values.StationConfiguration;
import java.util.List;

public class StationInitializationService {

    private static final double INITIAL_FILL_PERCENTAGE = 0.8;

    private final StationFactory stationFactory;
    private final ScooterFactory scooterFactory;
    private final StationRepository stationRepository;
    private final ScooterRepository scooterRepository;

    public StationInitializationService(
            StationFactory stationFactory,
            ScooterFactory scooterFactory,
            StationRepository stationRepository,
            ScooterRepository scooterRepository) {
        this.stationFactory = stationFactory;
        this.scooterFactory = scooterFactory;
        this.stationRepository = stationRepository;
        this.scooterRepository = scooterRepository;
    }

    public void initializeStations(List<StationConfiguration> stations) {
        stations.forEach(this::initializeStation);
    }

    private void initializeStation(StationConfiguration stationConfig) {
        Station station = stationFactory.create(stationConfig.location(), stationConfig.capacity());

        int initialScooterCount = calculateInitialScooterCount(stationConfig.capacity());
        List<Scooter> scooters =
                scooterFactory.create(initialScooterCount, stationConfig.location());

        for (int i = 0; i < scooters.size(); i++) {
            Scooter scooter = scooters.get(i);
            scooterRepository.save(scooter);
            station.returnScooter(new SlotNumber(i), scooter.getScooterId());
        }

        stationRepository.save(station);
    }

    private int calculateInitialScooterCount(int capacity) {
        return (int) Math.round(capacity * INITIAL_FILL_PERCENTAGE);
    }
}
