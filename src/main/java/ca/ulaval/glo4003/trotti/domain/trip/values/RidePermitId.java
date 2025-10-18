package ca.ulaval.glo4003.trotti.domain.trip.values;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

public class RidePermitId extends Id {
	
	public static RidePermitId from(String value) {
		return new RidePermitId(value);
	}

	private RidePermitId() {
		super();
	}

	private RidePermitId(String value) {
		super(value);
	}
}
