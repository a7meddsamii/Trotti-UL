package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.repositories.ScooterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// placeholder
public class InMemoryScooterRepository implements ScooterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryScooterRepository.class);

    @Override
    public void save(Scooter scooter) {
        LOGGER.info("Saving scooter {}", scooter.getId());
    }

    @Override
    public Scooter findById(String scooterId) {
        return null;
    }

    @Override
    public void update(Scooter scooter) {

    }
}
