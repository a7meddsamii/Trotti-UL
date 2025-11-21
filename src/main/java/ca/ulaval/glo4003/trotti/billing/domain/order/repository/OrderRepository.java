package ca.ulaval.glo4003.trotti.billing.domain.order.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findById(OrderId orderId);

    boolean exists(Idul buyerId, OrderStatus status);

    Optional<Order> findOngoingOrderFor(Idul buyerId);

    Optional<Order> findOrderFor(Idul buyerId, OrderId orderId);
}
