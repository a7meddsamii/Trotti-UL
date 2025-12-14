package ca.ulaval.glo4003.trotti.billing.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryOrderRepositoryTest {

    private static final Idul BUYER_IDUL = Idul.from("user1234");

    private Order pendingOrder;
    private Order completedOrder;

    private InMemoryOrderRepository orderRepository;

    @BeforeEach
    void setup() {
        orderRepository = new InMemoryOrderRepository();
        pendingOrder = new Order(OrderId.randomId(), BUYER_IDUL);
        completedOrder = new Order(OrderId.randomId(), BUYER_IDUL);
        completedOrder.confirm();

    }

    @Test
    void givenOrder_whenSave_thenOngoingOrderCanBeFound() {
        orderRepository.save(pendingOrder);

        Optional<Order> result = orderRepository.findOngoingOrderFor(BUYER_IDUL);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(pendingOrder, result.get());
    }

    @Test
    void givenOnlyNonPendingOrders_whenFindOngoingOrderFor_thenReturnsEmpty() {
        orderRepository.save(completedOrder);

        Optional<Order> result = orderRepository.findOngoingOrderFor(BUYER_IDUL);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenPendingAndNonPendingOrders_whenFindOngoingOrderFor_thenReturnsPendingOne() {
        orderRepository.save(completedOrder);
        orderRepository.save(pendingOrder);

        Optional<Order> result = orderRepository.findOngoingOrderFor(BUYER_IDUL);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(pendingOrder, result.get());
    }

}
