package ca.ulaval.glo4003.trotti.domain.trip.values;

import ca.ulaval.glo4003.trotti.domain.trip.exceptions.InvalidLocation;

public abstract sealed class Location permits StationLocation, EmptyLocation {
	private final String building;
	private final String spotName;
	
	public static Location empty() {
		return EmptyLocation.getInstance();
	}
	
	public static Location from(String building, String spotName) {
		return new StationLocation(building, spotName);
	}
	
	protected Location(String building, String spotName) {
		validate(building, spotName);
		this.building = building;
		this.spotName = spotName;
	}
	
	public boolean isEmpty(){
		return building.isEmpty() && spotName.isEmpty();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		
		Location location = (Location) o;
		return building.equals(location.building) && spotName.equals(location.spotName);
	}
	
	@Override
	public int hashCode() {
		return building.hashCode() + spotName.hashCode();
	}
	
	@Override
	public String toString() {
		return building + " - " + spotName;
	}
	
	private void validate(String building, String spotName) {
		if (building == null || spotName == null) {
			throw new InvalidLocation("Building and spot name cannot be null");
		}
	}
}
