package ca.ulaval.glo4003.trotti.domain.trip.station.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import java.util.ArrayList;
import java.util.List;

public class Station {

    private final String location;
    private final String name;
    private final Integer capacity;
    private final List<Id> scooterIds;

    public Station(String location, String name, Integer capacity) {
        this.location = location;
        this.name = name;
        this.capacity = capacity;
        this.scooterIds = new ArrayList<>(capacity);
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public List<Id> getScooterIds() {
        return scooterIds;
    }
}
