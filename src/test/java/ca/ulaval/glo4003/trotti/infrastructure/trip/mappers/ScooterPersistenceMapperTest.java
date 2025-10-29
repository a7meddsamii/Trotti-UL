package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.trip.domain.entities.Battery;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.fixtures.ScooterFixture;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.ScooterPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.BatteryRecord;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.ScooterRecord;
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
        BatteryRecord batteryRecord = toBatteryRecord(scooter.getBattery());
        ScooterRecord record =
                new ScooterRecord(scooter.getScooterId(), batteryRecord, scooter.getLocation());

        Scooter resultScooter = scooterMapper.toDomain(record);

        assertEqual(resultScooter, record);
    }

    private void assertEqual(Scooter scooter, ScooterRecord record) {
        Assertions.assertEquals(scooter.getScooterId(), record.id());
        assertEqual(scooter.getBattery(), record.batteryRecord());
        Assertions.assertEquals(scooter.getLocation(), record.location());
    }

    private void assertEqual(Battery battery, BatteryRecord record) {
        Assertions.assertEquals(battery.getBatteryLevel(), record.BatteryLevel());
        Assertions.assertEquals(battery.getLastBatteryUpdate(), record.lastBatteryUpdate());
        Assertions.assertEquals(battery.getCurrentBatteryState(), record.currentState());
    }

    private BatteryRecord toBatteryRecord(Battery battery) {
        return new BatteryRecord(battery.getBatteryLevel(), battery.getLastBatteryUpdate(),
                battery.getCurrentBatteryState());
    }
}
