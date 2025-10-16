package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidStation;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;

import java.util.List;

public class Station {
    private final Location location;
    private final List<Id> scootersInStation;
    private final int maximumCapacity;

    public Station(
            Location location,
            List<Id> scootersInStation,
            int maximumCapacity
    ) {
        this.location = location;
        this.scootersInStation = scootersInStation;
        this.maximumCapacity = maximumCapacity;
    }

    public void dockScooter(Id scooterId) {
        if (scootersInStation.contains(scooterId)) {
            throw new InvalidStation("Scooter is already in this station");
        }
        if (scootersInStation.size() == maximumCapacity) {
            throw new InvalidStation("Location is at capacity");
        }

        scootersInStation.add(scooterId);
    }

    public void undockScooter(Id scooterId) {
        if (!scootersInStation.contains(scooterId)) {
            throw new InvalidStation("Scooter is not in this station");
        }

        scootersInStation.remove(scooterId);
    }
}
