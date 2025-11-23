package ca.ulaval.glo4003.trotti.commons.domain.events.billing.order;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class OrderPlacedEvent extends Event {

    private final String orderId;

    public OrderPlacedEvent(Idul idul, String orderId) {
        super(idul, "order.placed");
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
