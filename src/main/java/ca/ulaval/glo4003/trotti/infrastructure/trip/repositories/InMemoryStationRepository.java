package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// placeholder
public class InMemoryStationRepository implements StationRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryStationRepository.class);

    @Override
    public void save(Station station) {
        LOGGER.info("Saving station {}", station.getStationLocation().getSpotName());
    }

    @Override
    public Station findById(String stationId) {
        return null;
    }

    @Override
    public void update(Station station) {

    }
}
