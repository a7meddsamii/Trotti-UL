package ca.ulaval.glo4003.trotti.billing.domain.order.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.OrderId;

public interface OrderRepository {
    void save(Order order);

    Order findById(OrderId orderId);

    boolean exists(Idul buyerId, OrderStatus status);
}
