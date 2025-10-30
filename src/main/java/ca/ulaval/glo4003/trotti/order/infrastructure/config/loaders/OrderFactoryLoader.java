package ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.domain.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.order.domain.factories.PassFactory;

public class OrderFactoryLoader extends Bootstrapper {

    @Override
    public void load() {
        loadOrderFactory();
        loadPassFactory();
    }

    private void loadOrderFactory() {
        this.resourceLocator.register(OrderFactory.class, new OrderFactory());
    }

    private void loadPassFactory() {
        this.resourceLocator.register(PassFactory.class, new PassFactory());
    }
}
