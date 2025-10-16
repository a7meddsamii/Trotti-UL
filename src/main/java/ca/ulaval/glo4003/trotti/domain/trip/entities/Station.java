package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidDock;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidUndock;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import java.util.List;

public class Station {
    private final Location location;
    private final List<Id> scootersInStation;
    private final int capacity;

    public Station(Location location, List<Id> scootersInStation, int capacity) {
        this.location = location;
        this.scootersInStation = scootersInStation;
        this.capacity = capacity;
    }

    public void dockScooter(Id scooterId) {
        if (scootersInStation.contains(scooterId)) {
            throw new InvalidDock("Scooter is already in this station");
        }
        if (scootersInStation.size() == capacity) {
            throw new InvalidDock("Station is at capacity");
        }

        scootersInStation.add(scooterId);
    }

    public void undockScooter(Id scooterId) {
        if (!scootersInStation.contains(scooterId)) {
            throw new InvalidUndock("Scooter is not in this station");
        }

        scootersInStation.remove(scooterId);
    }
}
