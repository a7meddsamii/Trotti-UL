package ca.ulaval.glo4003.trotti.billing.domain.order.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;

public class ItemId extends Id {

    private ItemId() {
        super();
    }

    private ItemId(String value) {
        super(value);
    }

    public static ItemId from(String value) {
        return new ItemId(value);
    }

    public static ItemId randomId() {
        return new ItemId();
    }
}
