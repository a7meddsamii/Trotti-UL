package ca.ulaval.glo4003.trotti.billing.domain.payment.values;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;

public class PaymentIntent {
	private final Idul buyerId;
	private final OrderId orderId;
	private final Money amount;
	private final PaymentMethod method;
	
	private PaymentIntent(Idul buyerId, OrderId orderId, Money amount, PaymentMethod method) {
		this.buyerId = buyerId;
		this.orderId = orderId;
		this.amount = amount;
		this.method = method;
	}
	
	public static PaymentIntent of(Idul buyerId, OrderId orderId, Money amount, PaymentMethod method) {
		return new PaymentIntent(buyerId, orderId, amount, method);
	}
	
	public Idul getBuyerId() {
		return buyerId;
	}
	
	public OrderId getOrderId() {
		return orderId;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public PaymentMethod getMethod() {
		return method;
	}
}
