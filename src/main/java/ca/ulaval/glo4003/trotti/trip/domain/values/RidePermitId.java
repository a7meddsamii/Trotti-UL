package ca.ulaval.glo4003.trotti.trip.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Id;

public class RidePermitId extends Id {

    private RidePermitId() {
        super();
    }

    private RidePermitId(String value) {
        super(value);
    }

    public static RidePermitId from(String value) {
        return new RidePermitId(value);
    }

    public static RidePermitId randomId() {
        return new RidePermitId();
    }
}
