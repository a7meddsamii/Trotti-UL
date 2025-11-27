package ca.ulaval.glo4003.trotti.fleet.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;

public class TransferId extends Id {

    private TransferId() {
        super();
    }

    private TransferId(String value) {
        super(value);
    }

    public static TransferId from(String value) {
        return new TransferId(value);
    }

    public static TransferId randomId() {
        return new TransferId();
    }
}
