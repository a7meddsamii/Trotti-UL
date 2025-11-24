package ca.ulaval.glo4003.trotti.commons.domain.events;

public interface EventBus {

    <T extends Event> void subscribe(Class<T> k, EventHandler<T> subscriber);

    void publish(Event event);
}
