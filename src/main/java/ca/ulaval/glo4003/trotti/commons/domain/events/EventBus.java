package ca.ulaval.glo4003.trotti.commons.domain.events;

public interface EventBus {

    void publish(Event event);
}
