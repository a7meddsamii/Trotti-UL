package ca.ulaval.glo4003.trotti.billing.domain.payment.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;

public class TransactionId extends Id {
	
	public static TransactionId from(String value) {
		return new TransactionId(value);
	}
	
	public static TransactionId create() {
		return new TransactionId();
	}
	
	private TransactionId(String value) {
		super(value);
	}
	
	private TransactionId() {
		super();
	}
}
