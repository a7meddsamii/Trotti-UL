package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.config.events.InMemoryEventBus;

public class EventBusLoader extends Bootstrapper {

    @Override
    public void load() {
        InMemoryEventBus eventBus = new InMemoryEventBus();
        this.resourceLocator.register(EventBus.class, eventBus);
    }
}
