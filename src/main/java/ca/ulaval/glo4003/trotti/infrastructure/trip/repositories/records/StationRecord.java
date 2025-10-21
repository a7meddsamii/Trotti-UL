package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;

import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.util.List;

public record StationRecord(Location location, List<ScooterId> dockedScooters, int capacity) {
}
