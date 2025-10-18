package ca.ulaval.glo4003.trotti.domain.order.values;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

public class PassId extends Id {
	
	public static PassId from(String value) {
		return new PassId(value);
	}
	
	public static PassId randomId() {
		return new PassId();
	} 
	
	private PassId() {
		super();
	}
	
	private PassId(String value) {
		super(value);
	}
}
