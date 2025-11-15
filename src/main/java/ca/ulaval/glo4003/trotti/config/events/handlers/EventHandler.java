package ca.ulaval.glo4003.trotti.config.events.handlers;

import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public interface EventHandler<T extends Event> {

    Class<T> eventType();

    void handle(T event);
}