package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.api.order.controller.OrderController;
import ca.ulaval.glo4003.trotti.billing.api.order.controller.OrderResource;
import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class OrderResourceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadOrderResourceLoader();
    }

    private void loadOrderResourceLoader() {
        OrderApplicationService orderApplicationService =
                this.resourceLocator.resolve(OrderApplicationService.class);
        OrderApiMapper orderApiMapper = this.resourceLocator.resolve(OrderApiMapper.class);
        OrderResource orderResource = new OrderController(orderApplicationService, orderApiMapper);
        this.resourceLocator.register(OrderResource.class, orderResource);
    }
}
