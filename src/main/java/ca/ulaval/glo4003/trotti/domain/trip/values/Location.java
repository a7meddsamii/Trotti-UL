package ca.ulaval.glo4003.trotti.domain.trip.values;

import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidLocationException;
import java.util.Objects;

public abstract class Location {
    private final String building;
    private final String spotName;

    protected Location(String building, String spotName) {
        validate(building, spotName);
        this.building = building;
        this.spotName = spotName;
    }

    public static Location empty() {
        return LocationEmpty.getInstance();
    }

    public static Location of(String building, String spotName) {
        return new StationLocation(building, spotName);
    }

    public static Location of(String building) {
        return new StationLocation(building, "");
    }

    public boolean isEmpty() {
        return building.isBlank() && spotName.isBlank();
    }

    public String getBuilding() {
        return building;
    }

    public String getSpotName() {
        return spotName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        Location location = (Location) o;

        return building.equals(location.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(building);
    }

    @Override
    public String toString() {
        return building + " - " + spotName;
    }

    private void validate(String building, String spotName) {
        if (building == null || spotName == null) {
            throw new InvalidLocationException("Building and spot name cannot be null");
        }
    }
}
