package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Battery;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.domain.values.BatteryLevel;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.fixtures.ScooterFixture;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.ScooterPersistenceMapper;
import java.math.BigDecimal;
import java.util.List;
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

    @Test
    void givenExistingScooters_whenFindByIds_thenReturnsAllMatchingScooters() {
        Scooter scooter1 = scooterFixture.withId(SCOOTER_ID).build();
        Scooter scooter2 = scooterFixture.withId(ANOTHER_SCOOTER_ID).build();
        scooterRepository.save(scooter1);
        scooterRepository.save(scooter2);

        List<Scooter> result = scooterRepository.findByIds(List.of(SCOOTER_ID, ANOTHER_SCOOTER_ID));

        Assertions.assertEquals(2, result.size());
        assertEqual(scooter1, result.get(0));
        assertEqual(scooter2, result.get(1));
    }

    @Test
    void givenUnknownId_whenFindByIds_thenUnknownIdIsIgnored() {
        Scooter scooter = scooterFixture.withId(SCOOTER_ID).build();
        scooterRepository.save(scooter);

        List<Scooter> result = scooterRepository.findByIds(List.of(SCOOTER_ID, ANOTHER_SCOOTER_ID));

        Assertions.assertEquals(1, result.size());
        assertEqual(scooter, result.get(0));
    }

    @Test
    void givenNoExistingIds_whenFindByIds_thenReturnsEmptyList() {
        List<Scooter> result = scooterRepository.findByIds(List.of(SCOOTER_ID, ANOTHER_SCOOTER_ID));

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenScooters_whenSaveAll_thenAllScootersAreSaved() {
        Scooter scooter1 = scooterFixture.withId(SCOOTER_ID).build();
        Scooter scooter2 = scooterFixture.withId(ANOTHER_SCOOTER_ID).build();
        scooterRepository.saveAll(List.of(scooter1, scooter2));

        Scooter retrieved1 = scooterRepository.findById(SCOOTER_ID);
        Scooter retrieved2 = scooterRepository.findById(ANOTHER_SCOOTER_ID);

        assertEqual(scooter1, retrieved1);
        assertEqual(scooter2, retrieved2);
    }

    private void assertEqual(Scooter expected, Scooter actual) {
        Assertions.assertEquals(expected.getScooterId(), actual.getScooterId());
        assertEqual(expected.getBattery(), actual.getBattery());
        Assertions.assertEquals(expected.getLocation(), actual.getLocation());
    }

    private void assertEqual(Battery expected, Battery actual) {
        Assertions.assertEquals(expected.getBatteryLevel(), actual.getBatteryLevel());
        Assertions.assertEquals(expected.getLastBatteryUpdate(), actual.getLastBatteryUpdate());
        Assertions.assertEquals(expected.getCurrentBatteryState(), actual.getCurrentBatteryState());
    }
}
