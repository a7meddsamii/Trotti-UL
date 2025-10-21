package ca.ulaval.glo4003.trotti.domain.trip.entities;

import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidDock;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidUndock;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.util.List;

public class Station {
    private final Location location;
    private final List<ScooterId> dockedScooters;
    private final int capacity;

    public Station(Location location, List<ScooterId> dockedScooters, int capacity) {
        this.location = location;
        this.dockedScooters = dockedScooters;
        this.capacity = capacity;
    }

    public void dockScooter(ScooterId scooterId) {
        if (dockedScooters.contains(scooterId)) {
            throw new InvalidDock("Scooter is already in this station");
        }
        if (dockedScooters.size() == capacity) {
            throw new InvalidDock("Station is at capacity");
        }

        dockedScooters.add(scooterId);
    }

    public void undockScooter(ScooterId scooterId) {
        if (!dockedScooters.contains(scooterId)) {
            throw new InvalidUndock("Scooter is not in this station");
        }

        dockedScooters.remove(scooterId);
    }

    public Location getLocation() {
        return location;
    }

    public List<ScooterId> getDockedScooters() {
        return List.copyOf(dockedScooters);
    }

    public int getCapacity() {
        return capacity;
    }
}
