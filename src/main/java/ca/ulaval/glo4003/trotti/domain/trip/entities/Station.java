package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.ArrayList;
import java.util.List;

public class Station {

    private static final double INITIAL_FILL_RATIO = 0.8;

    private final Id stationId;
    private final Location stationLocation;
    private final Integer capacity;
    private final List<Id> scooterIds;

    public Station(Location stationLocation, Integer capacity) {
        this.stationId = Id.randomId();
        this.stationLocation = stationLocation;
        this.capacity = capacity;
        this.scooterIds = new ArrayList<>(capacity);
    }

    public Id getStationId() {
        return stationId;
    }

    public Location getStationLocation() {
        return stationLocation;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public List<Id> getScooterIds() {
        return scooterIds;
    }

    public void addScooter(Id scooterId) {
        scooterIds.add(scooterId);
    }

    public int getInitialScooterCount() {
        return (int) Math.floor(capacity * INITIAL_FILL_RATIO);
    }
}
