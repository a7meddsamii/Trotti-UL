package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Battery;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;

public record ScooterRecord(Id id, Battery battery, Location location) {
}
