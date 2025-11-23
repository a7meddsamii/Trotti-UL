package ca.ulaval.glo4003.trotti.billing.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<OrderId, Order> database = new HashMap<>();
    
    @Override
    public void save(Order order) {
        database.put(order.getOrderId(), order);
    }
    
    @Override
    public Optional<Order> findOngoingOrderFor(Idul buyerId) {
        return database.values().stream()
                .filter(order -> order.getBuyerId().equals(buyerId))
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .findFirst();
    }
}