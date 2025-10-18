package ca.ulaval.glo4003.trotti.domain.order.values;

import ca.ulaval.glo4003.trotti.domain.commons.Id;

public class OrderId extends Id {

    public static OrderId from(String value) {
        return new OrderId(value);
    }

    public static OrderId randomId() {
        return new OrderId();
    }

    private OrderId() {
        super();
    }

    private OrderId(String value) {
        super(value);
    }
}
