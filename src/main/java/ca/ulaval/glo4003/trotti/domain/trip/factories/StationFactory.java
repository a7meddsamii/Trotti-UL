package ca.ulaval.glo4003.trotti.domain.trip.factories;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidStation;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.List;

public class StationFactory {
    public Station create(Location location, List<Id> scootersInStation, int capacity) {
        validateCapacity(capacity);
        return new Station(location, scootersInStation, capacity);
    }

    private void validateCapacity(int capacity) {
        if (capacity <= 0) {
            throw new InvalidStation("Station maximum capacity cannot be 0 or negative");
        }
    }
}
