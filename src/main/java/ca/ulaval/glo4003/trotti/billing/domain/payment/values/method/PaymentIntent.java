package ca.ulaval.glo4003.trotti.billing.domain.payment.values.method;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

public class PaymentIntent {
    private final Idul buyerId;
    private final OrderId orderId;
    private final Money amount;
    private final PaymentMethod method;
    private final boolean useSavedInfo;

    private PaymentIntent(
            Idul buyerId,
            OrderId orderId,
            Money amount,
            PaymentMethod method,
            boolean useSavedInfo) {
        this.buyerId = buyerId;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.useSavedInfo = useSavedInfo;
    }

    public static PaymentIntent of(Idul buyerId, OrderId orderId, Money amount,
            PaymentMethod method, boolean useSavedInfo) {
        return new PaymentIntent(buyerId, orderId, amount, method, useSavedInfo);
    }

    public boolean isForPaymentMethodType(PaymentMethodType type) {
        return !method.isEmpty() && method.isType(type);
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

    public boolean useSavedInfo() {
        return useSavedInfo;
    }
}
