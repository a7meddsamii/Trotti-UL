package ca.ulaval.glo4003.trotti.domain.order.values;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

public class TransactionId extends Id {
	
	public static TransactionId from(String value) {
		return new TransactionId(value);
	}

	public static TransactionId randomId() {
		return new TransactionId();
	}

	private TransactionId() {
		super();
	}

	private TransactionId(String value) {
		super(value);
	}
}
