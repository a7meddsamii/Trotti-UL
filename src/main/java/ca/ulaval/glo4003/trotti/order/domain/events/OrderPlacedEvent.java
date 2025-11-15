package ca.ulaval.glo4003.trotti.order.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;

import java.util.List;

public class OrderPlacedEvent extends Event {

    private final String orderId;
    private final List<String> ridePermitIds;
    private final Money totalPrice;
    private final String paymentMethodType;

    public OrderPlacedEvent(Idul idul,
                            List<String> ridePermitIds,
                            String orderId,
                            Money totalPrice,
                            String paymentMethodType) {
        super(idul, "order.placed");
        this.orderId = orderId;
        this.ridePermitIds = ridePermitIds;
        this.totalPrice = totalPrice;
        this.paymentMethodType = paymentMethodType;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<String> getRidePermitIds() {
        return ridePermitIds;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }
}
