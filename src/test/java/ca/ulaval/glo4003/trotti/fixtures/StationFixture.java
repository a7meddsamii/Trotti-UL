package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Station;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.ArrayList;
import java.util.List;

public class StationFixture {
    public static final Location A_LOCATION = Location.of("vachon", "stationX");
    public static final List<Id> DOCKED_SCOOTERS = new ArrayList<>();
    public static final int A_CAPACITY = 10;

    private Location location = A_LOCATION;
    private List<Id> dockedScooters = DOCKED_SCOOTERS;
    private int capacity = A_CAPACITY;

    public StationFixture withLocation(Location location) {
        this.location = location;
        return this;
    }

    public StationFixture withDockedScooters(List<Id> dockedScooters) {
        this.dockedScooters = dockedScooters;
        return this;
    }

    public StationFixture withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Station build() {
        return new Station(location, dockedScooters, capacity);
    }

}
