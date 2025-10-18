package ca.ulaval.glo4003.trotti.domain.order.values;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

public class InvoiceId extends Id {
	
	public static InvoiceId from(String value) {
		return new InvoiceId(value);
	}

	public static InvoiceId randomId() {
		return new InvoiceId();
	}

	private InvoiceId() {
		super();
	}

	private InvoiceId(String value) {
		super(value);
	}
}
