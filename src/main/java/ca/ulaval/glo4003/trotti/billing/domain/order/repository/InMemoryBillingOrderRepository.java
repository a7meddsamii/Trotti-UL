package ca.ulaval.glo4003.trotti.billing.domain.order.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBillingOrderRepository implements OrderRepository {
    private final Map<ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId, Order> database = new HashMap<>();
    
    @Override
    public void save(Order order) {
        database.put(order.getOrderId(), order);
    }
    
    @Override
    public Optional<Order> findById(OrderId orderId) {
        return Optional.ofNullable(database.get(orderId));
    }
    
    @Override
    public boolean exists(Idul buyerId, OrderStatus status) {
        return database.values().stream()
                .anyMatch(order ->
                        order.getBuyerId().equals(buyerId) &&
                                order.getStatus() == status
                );
    }
}