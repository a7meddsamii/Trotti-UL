package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.domain.trip.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import ca.ulaval.glo4003.trotti.fixtures.ScooterFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.mappers.ScooterPersistenceMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryScooterRepositoryIntegrationTest {

    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final ScooterId ANOTHER_SCOOTER_ID = ScooterId.randomId();

    private InMemoryScooterRepository scooterRepository;
    private ScooterFixture scooterFixture;

    @BeforeEach
    void setup() {
        ScooterPersistenceMapper mapper = new ScooterPersistenceMapper();
        scooterRepository = new InMemoryScooterRepository(mapper);
        scooterFixture = new ScooterFixture();
    }

    @Test
    void givenAScooter_whenSaving_thenItIsSaved() {
        Scooter scooter = scooterFixture.withId(SCOOTER_ID).build();

        scooterRepository.save(scooter);
        Scooter retrievedScooter = scooterRepository.findById(SCOOTER_ID);

        assertEqual(scooter, retrievedScooter);
    }

    @Test
    void givenDifferentScooters_whenSaving_thenTheyCanBeRetrieved() {
        Scooter scooter1 = scooterFixture.withId(SCOOTER_ID).build();
        Scooter scooter2 = scooterFixture.withId(ANOTHER_SCOOTER_ID).build();

        scooterRepository.save(scooter1);
        scooterRepository.save(scooter2);

        Scooter retrievedScooter1 = scooterRepository.findById(SCOOTER_ID);
        Scooter retrievedScooter2 = scooterRepository.findById(ANOTHER_SCOOTER_ID);

        assertEqual(scooter1, retrievedScooter1);
        assertEqual(scooter2, retrievedScooter2);
    }

    @Test
    void givenExistingScooter_whenSavingAgain_thenItIsUpdated() {
        Scooter originalScooter = scooterFixture.withId(SCOOTER_ID).build();
        scooterRepository.save(originalScooter);

        BatteryLevel newBatteryLevel = BatteryLevel.from(BigDecimal.valueOf(50));
        Scooter updatedScooter =
                scooterFixture.withId(SCOOTER_ID).withBatteryLevel(newBatteryLevel).build();
        scooterRepository.save(updatedScooter);

        Scooter retrievedScooter = scooterRepository.findById(SCOOTER_ID);

        assertEqual(updatedScooter, retrievedScooter);
    }

    private void assertEqual(Scooter expected, Scooter actual) {
        Assertions.assertEquals(expected.getScooterId(), actual.getScooterId());
        Assertions.assertEquals(expected.getBattery(), actual.getBattery());
        Assertions.assertEquals(expected.getLocation(), actual.getLocation());
    }
}
