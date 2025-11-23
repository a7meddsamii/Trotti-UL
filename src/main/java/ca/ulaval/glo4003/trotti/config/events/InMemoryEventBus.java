package ca.ulaval.glo4003.trotti.config.events;

import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEventBus implements EventBus {

    private final Map<Class<? extends Event>, List<EventHandler<? extends Event>>> listeners =
            new ConcurrentHashMap<>();

    public <T extends Event> void subscribe(Class<T> k, EventHandler<T> subscriber) {
        listeners.computeIfAbsent(k, v -> new ArrayList<>()).add(subscriber);
    }

    @Override
    public void publish(Event event) {
        List<EventHandler<?>> handlers =
                listeners.getOrDefault(event.getClass(), Collections.emptyList());

        for (EventHandler<?> handler : handlers) {
            invokeHandler(handler, event);
        }
    }

    private <T extends Event> void invokeHandler(EventHandler<?> rawHandler, T event) {
        EventHandler<? super T> handler = (EventHandler<? super T>) rawHandler;
        handler.handle(event);
    }
}
