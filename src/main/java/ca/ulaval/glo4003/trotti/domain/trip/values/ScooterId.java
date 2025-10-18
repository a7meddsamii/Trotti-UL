package ca.ulaval.glo4003.trotti.domain.trip.values;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

public class ScooterId extends Id {
	
	public static ScooterId from(String value) {
		return new ScooterId(value);
	}

	public static ScooterId randomId() {
		return new ScooterId();
	}

	private ScooterId() {
		super();
	}

	private ScooterId(String value) {
		super(value);
	}
}
