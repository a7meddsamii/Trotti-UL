package ca.ulaval.glo4003.trotti.commons.domain.events;

import ca.ulaval.glo4003.trotti.commons.domain.Id;

public class EventId extends Id {

    private EventId() {
        super();
    }

    private EventId(String value) {
        super(value);
    }

    public static EventId from(String value) {
        return new EventId(value);
    }

    public static EventId randomId() {
        return new EventId();
    }
}
