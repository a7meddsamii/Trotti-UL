package ca.ulaval.glo4003.trotti.commons.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;

import java.time.Instant;

public abstract class Event {

    private final EventId eventId;
    private final Idul idul;
    private final String eventType;
    private final Instant timestamp;

    protected Event(Idul idul, String eventType) {
        this.idul = idul;
        this.eventId = EventId.randomId();
        this.eventType = eventType;
        this.timestamp = Instant.now();
    }

    public EventId getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Idul getIdul() {
        return idul;
    }
}
