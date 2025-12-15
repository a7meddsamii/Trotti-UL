package ca.ulaval.glo4003.trotti.fleet.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;

public class ScooterId extends Id {

    private ScooterId() {
        super();
    }

    private ScooterId(String value) {
        super(value);
    }

    public static ScooterId from(String value) {
        return new ScooterId(value);
    }

    public static ScooterId randomId() {
        return new ScooterId();
    }
}
