package ca.ulaval.glo4003.trotti.config.events.handlers;

import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

@FunctionalInterface
public interface EventHandler<T extends Event> {

    void handle(T event);
}
