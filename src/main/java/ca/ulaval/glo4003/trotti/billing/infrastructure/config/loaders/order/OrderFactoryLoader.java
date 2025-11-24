package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.domain.order.factory.OrderItemFactory;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class OrderFactoryLoader extends Bootstrapper {

    @Override
    public void load() {
        loadOrderItemFactory();
    }

    private void loadOrderItemFactory() {
        OrderItemFactory orderItemFactory = new OrderItemFactory();
        this.resourceLocator.register(OrderItemFactory.class, orderItemFactory);
    }
}
