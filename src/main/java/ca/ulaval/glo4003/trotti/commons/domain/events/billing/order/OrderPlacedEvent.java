package ca.ulaval.glo4003.trotti.commons.domain.events.billing.order;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import java.util.List;

public class OrderPlacedEvent extends Event {

    private final String orderId;
    private final List<RidePermitItemSnapshot> ridePermitItems;

    public OrderPlacedEvent(
            Idul idul,
            String orderId,
            List<RidePermitItemSnapshot> ridePermitItems) {
        super(idul, "order.placed");
        this.orderId = orderId;
        this.ridePermitItems = ridePermitItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<RidePermitItemSnapshot> getRidePermitItems() {
        return List.copyOf(ridePermitItems);
    }
}
