package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.fixtures.ScooterFixture;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.ScooterRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScooterPersistenceMapperTest {

    private ScooterPersistenceMapper scooterMapper;
    private ScooterFixture scooterFixture;

    @BeforeEach
    void setup() {
        scooterMapper = new ScooterPersistenceMapper();
        scooterFixture = new ScooterFixture();
    }

    @Test
    void givenScooter_whenToRecord_thenReturnCorrespondingScooterRecord() {
        Scooter scooter = scooterFixture.build();

        ScooterRecord record = scooterMapper.toRecord(scooter);

        assertEqual(scooter, record);
    }

    @Test
    void givenScooterRecord_whenToDomain_thenReturnCorrespondingScooter() {
        Scooter scooter = scooterFixture.build();
        ScooterRecord record =
                new ScooterRecord(scooter.getId(), scooter.getBattery(), scooter.getLocation());

        Scooter resultScooter = scooterMapper.toDomain(record);

        assertEqual(resultScooter, record);
    }

    private void assertEqual(Scooter scooter, ScooterRecord record) {
        Assertions.assertEquals(scooter.getId(), record.id());
        Assertions.assertEquals(scooter.getBattery(), record.battery());
        Assertions.assertEquals(scooter.getLocation(), record.location());
    }
}
