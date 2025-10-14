package ca.ulaval.glo4003.trotti.domain.trip.station.entities;

public class StationLocation {

    private final String location;
    private final String name;

    public StationLocation(String location, String name) {
        this.location = location;
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        StationLocation stationLocation = (StationLocation) object;

        return location.equals(stationLocation.location) && name.equals(stationLocation.name);
    }
}
