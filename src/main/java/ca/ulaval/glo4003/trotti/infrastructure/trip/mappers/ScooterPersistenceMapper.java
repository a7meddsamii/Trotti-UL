package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.Scooter;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.ScooterRecord;

public class ScooterPersistenceMapper {

    public ScooterRecord toRecord(Scooter scooter) {
        return new ScooterRecord(scooter.getId(), scooter.getBattery(), scooter.getLocation());
    }

    public Scooter toDomain(ScooterRecord record) {
        return new Scooter(record.id(), record.battery(), record.location());
    }
}
