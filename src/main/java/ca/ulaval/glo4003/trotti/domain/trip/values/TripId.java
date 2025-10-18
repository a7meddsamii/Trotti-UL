package ca.ulaval.glo4003.trotti.domain.trip.values;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

public class TripId extends Id {
	
	public static TripId from(String value) {
		return new TripId(value);
	}

	public static TripId randomId() {
		return new TripId();
	}

	private TripId() {
		super();
	}

	private TripId(String value) {
		super(value);
	}
}
