package ca.ulaval.glo4003.trotti.trip.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;

public class TripId extends Id {

    private TripId() {
        super();
    }

    private TripId(String value) {
        super(value);
    }

    public static TripId from(String value) {
        return new TripId(value);
    }

    public static TripId randomId() {
        return new TripId();
    }
}
