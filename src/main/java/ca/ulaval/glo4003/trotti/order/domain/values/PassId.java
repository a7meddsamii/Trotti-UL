package ca.ulaval.glo4003.trotti.order.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.values.Id;

public class PassId extends Id {

    private PassId() {
        super();
    }

    private PassId(String value) {
        super(value);
    }

    public static PassId from(String value) {
        return new PassId(value);
    }

    public static PassId randomId() {
        return new PassId();
    }
}
