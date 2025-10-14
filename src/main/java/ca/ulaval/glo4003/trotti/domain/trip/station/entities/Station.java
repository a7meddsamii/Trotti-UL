package ca.ulaval.glo4003.trotti.domain.trip.station.entities;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import java.util.ArrayList;
import java.util.List;

public class Station {

    private final Id stationId;
    private final StationLocation stationLocation;
    private final Integer capacity;
    private final List<Id> scooterIds;

    public Station(StationLocation stationLocation, Integer capacity) {
        this.stationId = Id.randomId();
        this.stationLocation = stationLocation;
        this.capacity = capacity;
        this.scooterIds = new ArrayList<>(capacity);
    }

    public Id getStationId() {
        return stationId;
    }

    public StationLocation getStationLocation() {
        return stationLocation;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public List<Id> getScooterIds() {
        return scooterIds;
    }
}
