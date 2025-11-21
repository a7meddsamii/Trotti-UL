package ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.order.api.controllers.CartController;
import ca.ulaval.glo4003.trotti.order.api.controllers.CartResource;
import ca.ulaval.glo4003.trotti.order.api.controllers.OrderController;
import ca.ulaval.glo4003.trotti.order.api.controllers.OrderResource;
import ca.ulaval.glo4003.trotti.order.api.mappers.OrderApiMapper;
import ca.ulaval.glo4003.trotti.order.api.mappers.PassApiMapper;
import ca.ulaval.glo4003.trotti.order.application.CartApplicationService;
import ca.ulaval.glo4003.trotti.order.application.OrderApplicationService;

public class OrderResourceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadOrderResource();
        loadCartResource();
    }

    private void loadOrderResource() {
        OrderApiMapper orderApiMapper = this.resourceLocator.resolve(OrderApiMapper.class);
        OrderApplicationService orderApplicationService =
                this.resourceLocator.resolve(OrderApplicationService.class);

        OrderResource orderController =
                new OrderController(orderApplicationService, orderApiMapper);
        this.resourceLocator.register(OrderResource.class, orderController);
    }

    private void loadCartResource() {
        PassApiMapper passApiMapper = this.resourceLocator.resolve(PassApiMapper.class);
        CartApplicationService cartApplicationService =
                this.resourceLocator.resolve(CartApplicationService.class);

        CartResource cartController = new CartController(cartApplicationService, passApiMapper);
        this.resourceLocator.register(CartResource.class, cartController);
    }
}
