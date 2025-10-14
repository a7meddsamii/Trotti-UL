package ca.ulaval.glo4003.trotti.domain.trip.values;

import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidLocation;

class StationLocation extends Location {
	public StationLocation(String building, String spotName) {
		super(building, spotName);
		validate(building, spotName);
	}
	
	private void validate(String building, String spotName) {
		if (building.isBlank() || spotName.isBlank()) {
			throw new InvalidLocation("Building and spot name cannot be empty");
		}
	}
}