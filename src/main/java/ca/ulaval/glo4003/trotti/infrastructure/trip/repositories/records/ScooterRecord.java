package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;


import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;

public record ScooterRecord(ScooterId id, BatteryRecord batteryRecord, Location location) {
}
