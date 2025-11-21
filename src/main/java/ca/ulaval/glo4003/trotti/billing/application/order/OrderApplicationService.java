package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.ConfirmOrderDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.factory.OrderItemFactory;
import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentIntent;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.order.domain.events.OrderPlacedEvent;

public class OrderApplicationService {
    private final PaymentGateway paymentGateway;
    private final OrderRepository orderRepository;
    private final OrderAssembler orderAssembler;
    private final OrderItemFactory orderItemFactory;
    private final EventBus eventBus;

    public OrderApplicationService(
            PaymentGateway paymentGateway,
            OrderRepository orderRepository,
            OrderAssembler orderAssembler,
            OrderItemFactory orderItemFactory,
            EventBus eventBus) {
        this.paymentGateway = paymentGateway;
        this.orderRepository = orderRepository;
        this.orderAssembler = orderAssembler;
        this.orderItemFactory = orderItemFactory;
        this.eventBus = eventBus;
    }

    public OrderDto addItem(Idul buyerId, AddItemDto addItemDto) {
        Order order =
                orderRepository.findOngoingOrderFor(buyerId).orElseGet(() -> new Order(OrderId.randomId(), buyerId));
        order.add(orderItemFactory.create(addItemDto.maximumDailyTravelTime(), addItemDto.session(),
                addItemDto.billingFrequency()));
        orderRepository.save(order);

        return orderAssembler.assemble(order);
    }

    public OrderDto getOngoingOrder(Idul buyerId) {
        Order order = findOngoingOrder(buyerId);
        return orderAssembler.assemble(order);
    }

    public OrderDto getOrder(Idul buyerId, OrderId orderId) {
        Order order = orderRepository.findOrderFor(buyerId, orderId)
                .orElseThrow(() -> new NotFoundException("No ongoing order for buyer " + buyerId));

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

	//TODO
    public void confirm(Idul buyerId, ConfirmOrderDto confirmOrderDto) {
        Order order = findOngoingOrder(buyerId);
        boolean success = paymentGateway.pay(
                PaymentIntent.of(buyerId, order.getOrderId(), order.getTotalCost(), null));
        orderRepository.save(order);

        OrderPlacedEvent orderPlacedEvent = orderAssembler.assembleOrderPlacedEvent(order, success);
        eventBus.publish(orderPlacedEvent);
    }

    private Order findOngoingOrder(Idul buyerId) {
        return orderRepository.findOngoingOrderFor(buyerId)
                .orElseThrow(() -> new NotFoundException("No ongoing order for buyer " + buyerId));
    }
}
