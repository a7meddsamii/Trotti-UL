package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.application.order.OrderAssembler;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class OrderAssemblerLoader extends Bootstrapper {

    @Override
    public void load() {
        loadOrderAssembler();
    }

    private void loadOrderAssembler() {
        OrderAssembler orderAssembler = new OrderAssembler();
        this.resourceLocator.register(OrderAssembler.class, orderAssembler);
    }
}
