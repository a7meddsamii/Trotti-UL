package ca.ulaval.glo4003.trotti.billing.domain.order.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;

public class OrderId extends Id {

    private OrderId() {
        super();
    }

    private OrderId(String value) {
        super(value);
    }

    public static OrderId from(String value) {
        return new OrderId(value);
    }

    public static OrderId randomId() {
        return new OrderId();
    }
}
