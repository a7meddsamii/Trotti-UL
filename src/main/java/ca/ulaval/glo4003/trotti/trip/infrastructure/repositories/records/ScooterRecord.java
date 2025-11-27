package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;


import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;

public record ScooterRecord(ScooterId id, BatteryRecord batteryRecord, Location location) {
}
