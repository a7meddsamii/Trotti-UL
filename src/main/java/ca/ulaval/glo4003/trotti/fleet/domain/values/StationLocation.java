package ca.ulaval.glo4003.trotti.fleet.domain.values;

import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidLocationException;

final class StationLocation extends Location {
    public StationLocation(String building, String spotName) {
        super(building, spotName);
        validate(building);
    }

    private void validate(String building) {
        if (building.isBlank()) {
            throw new InvalidLocationException("Building and spot name cannot be empty");
        }
    }
}
