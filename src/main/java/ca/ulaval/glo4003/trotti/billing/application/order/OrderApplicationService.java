package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.ConfirmOrderDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.RidePermitItem;
import ca.ulaval.glo4003.trotti.billing.domain.order.factory.OrderItemFactory;
import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;

public class OrderApplicationService {
    private final OrderRepository orderRepository;
    private final OrderAssembler orderAssembler;
    private final OrderItemFactory orderItemFactory;
    private final PaymentMethodFactory paymentMethodFactory;
    private final PaymentGateway paymentGateway;
    private final EventBus eventBus;

    public OrderApplicationService(
            OrderRepository orderRepository,
            OrderAssembler orderAssembler,
            OrderItemFactory orderItemFactory,
            PaymentMethodFactory paymentMethodFactory,
            PaymentGateway paymentGateway,
            EventBus eventBus) {
        this.orderRepository = orderRepository;
        this.orderAssembler = orderAssembler;
        this.orderItemFactory = orderItemFactory;
        this.paymentMethodFactory = paymentMethodFactory;
        this.paymentGateway = paymentGateway;
        this.eventBus = eventBus;
    }

    public OrderDto addItem(Idul buyerId, AddItemDto addItemDto) {
        Order order = orderRepository.findOngoingOrderFor(buyerId)
                .orElseGet(() -> new Order(OrderId.randomId(), buyerId));
        RidePermitItem item = orderItemFactory.create(addItemDto.maximumDailyTravelTime(),
                addItemDto.session(), addItemDto.billingFrequency());
        order.add(item);
        orderRepository.save(order);

        return orderAssembler.assemble(order);
    }

    public OrderDto getOngoingOrder(Idul buyerId) {
        Order order = findOngoingOrder(buyerId);
        return orderAssembler.assemble(order);
    }

    public OrderDto removeItem(Idul buyerId, ItemId itemId) {
        Order order = findOngoingOrder(buyerId);
        order.remove(itemId);
        orderRepository.save(order);

        return orderAssembler.assemble(order);
    }

    public OrderDto removeAllItems(Idul buyerId) {
        Order order = findOngoingOrder(buyerId);
        order.clear();
        orderRepository.save(order);

        return orderAssembler.assemble(order);
    }

    public void confirm(Idul buyerId, ConfirmOrderDto confirmOrderDto) {
        // TODO coming in next pr
        Order order = findOngoingOrder(buyerId);
        orderRepository.save(order);
    }

    private Order findOngoingOrder(Idul buyerId) {
        return orderRepository.findOngoingOrderFor(buyerId)
                .orElseThrow(() -> new NotFoundException("No ongoing order for buyer " + buyerId));
    }
}
