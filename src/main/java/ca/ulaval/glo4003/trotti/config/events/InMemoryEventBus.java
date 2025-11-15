package ca.ulaval.glo4003.trotti.config.events;

import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.config.events.handlers.EventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryEventBus implements EventBus {

    private Map<Class<? extends Event>, List<EventHandler<? extends Event>>> listeners = new HashMap<>();

    public <T extends Event> void subscribe(EventHandler<T> subscriber) {
        listeners.computeIfAbsent(subscriber.eventType(), k -> new ArrayList<>())
                .add(subscriber);
    }

    @Override
    public void publish(Event event) {
        List<EventHandler<?>> handlers = listeners.getOrDefault(event.getClass(), Collections.emptyList());

        for (EventHandler<?> handler : handlers) {
           handle(handler, event);
        }
    }

    private <T extends Event> void handle(EventHandler<T> handler, Event event) {
        T castedEvent = handler.eventType().cast(event);
        handler.handle(castedEvent);
    }
}
