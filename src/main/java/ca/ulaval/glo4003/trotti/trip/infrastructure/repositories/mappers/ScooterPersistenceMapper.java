package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.fleet.domain.entities.Battery;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Scooter;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.BatteryRecord;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.ScooterRecord;

public class ScooterPersistenceMapper {

    public ScooterRecord toRecord(Scooter scooter) {
        BatteryRecord batteryRecord = toRecordBattery(scooter.getBattery());
        return new ScooterRecord(scooter.getScooterId(), batteryRecord, scooter.getLocation());
    }

    public Scooter toDomain(ScooterRecord record) {
        Battery battery = toDomainBattery(record.batteryRecord());
        return new Scooter(record.id(), battery, record.location());
    }

    private Battery toDomainBattery(BatteryRecord BatteryRecord) {
        return new Battery(BatteryRecord.BatteryLevel(), BatteryRecord.lastBatteryUpdate(),
                BatteryRecord.currentState());
    }

    private BatteryRecord toRecordBattery(Battery battery) {
        return new BatteryRecord(battery.getBatteryLevel(), battery.getLastBatteryUpdate(),
                battery.getCurrentBatteryState());
    }
}
