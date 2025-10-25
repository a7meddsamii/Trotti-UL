package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.BatteryRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.ScooterRecord;

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
        return  new BatteryRecord( battery.getBatteryLevel(), battery.getLastBatteryUpdate(), battery.getCurrentBatteryState());
    }
}
