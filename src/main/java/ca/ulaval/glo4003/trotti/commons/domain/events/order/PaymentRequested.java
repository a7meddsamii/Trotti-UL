package ca.ulaval.glo4003.trotti.commons.domain.events.order;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;

public class PaymentRequested extends Event {
	String orderId;
	String paymentMethod;
	Money totalPrice;
	
	
	protected PaymentRequested(Idul idul) {
		super(idul, "order.payment.requested");
	}
}
