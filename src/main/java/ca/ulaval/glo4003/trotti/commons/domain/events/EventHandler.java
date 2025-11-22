package ca.ulaval.glo4003.trotti.commons.domain.events;

@FunctionalInterface
public interface EventHandler<T extends Event> {

    void handle(T event);
}
