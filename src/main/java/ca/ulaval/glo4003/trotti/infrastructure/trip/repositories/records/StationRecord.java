package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.List;

public record StationRecord(Location location, List<Id> dockedScooters, int capacity) {
}
