package ca.ulaval.glo4003.trotti.domain.trip.services;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.factories.ScooterFactory;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import java.util.List;

public class StationInitializationService {

    private final ScooterFactory scooterFactory;
    private final StationRepository stationRepository;
    private final ScooterRepository scooterRepository;

    public StationInitializationService(
            ScooterFactory scooterFactory,
            StationRepository stationRepository,
            ScooterRepository scooterRepository) {
        this.scooterFactory = scooterFactory;
        this.stationRepository = stationRepository;
        this.scooterRepository = scooterRepository;
    }

    public void initializeStations(List<Station> stations) {
        stations.forEach(this::initializeStation);
    }

    private void initializeStation(Station station) {
        int initialScooterCount = station.getInitialScooterCount();
        List<Scooter> scooters = scooterFactory.createScooters(initialScooterCount, station.getStationLocation());

        scooters.forEach(scooter -> {
            scooterRepository.save(scooter);
            station.addScooter(scooter.getId());
        });

        stationRepository.save(station);
    }
}
