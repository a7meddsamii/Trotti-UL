package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.application.order.OrderApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.order.OrderAssembler;
import ca.ulaval.glo4003.trotti.billing.domain.order.factory.OrderItemFactory;
import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class OrderApplicationServiceLoader extends Bootstrapper {

    @Override
    public void load() {
        loadOrderApplicationService();
    }

    private void loadOrderApplicationService() {
        OrderRepository orderRepository = this.resourceLocator.resolve(OrderRepository.class);
        OrderAssembler orderAssembler = this.resourceLocator.resolve(OrderAssembler.class);
        OrderItemFactory orderItemFactory = this.resourceLocator.resolve(OrderItemFactory.class);
        PaymentMethodFactory paymentMethodFactory =
                this.resourceLocator.resolve(PaymentMethodFactory.class);
        PaymentGateway paymentGateway = this.resourceLocator.resolve(PaymentGateway.class);
        EventBus eventBus = this.resourceLocator.resolve(EventBus.class);
        OrderApplicationService orderApplicationService =
                new OrderApplicationService(orderRepository, orderAssembler, orderItemFactory,
                        paymentMethodFactory, paymentGateway, eventBus);
        this.resourceLocator.register(OrderApplicationService.class, orderApplicationService);
    }
}
