package ca.ulaval.glo4003.trotti.billing.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryOrderRepositoryTest {
    private Order pendingOrder;
    private Order paidOrder;
    private OrderId pendingOrderId;
    private OrderId paidOrderId;
    private Idul buyerId;
    
    private InMemoryOrderRepository orderRepository;
    
    @BeforeEach
    void setup() {
        orderRepository = new InMemoryOrderRepository();
        
        pendingOrder = Mockito.mock(Order.class);
        paidOrder = Mockito.mock(Order.class);
        pendingOrderId = Mockito.mock(OrderId.class);
        paidOrderId = Mockito.mock(OrderId.class);
        buyerId = Mockito.mock(Idul.class);
        
        Mockito.when(pendingOrder.getOrderId()).thenReturn(pendingOrderId);
        Mockito.when(paidOrder.getOrderId()).thenReturn(paidOrderId);
        Mockito.when(pendingOrder.getBuyerId()).thenReturn(buyerId);
        Mockito.when(paidOrder.getBuyerId()).thenReturn(buyerId);
        Mockito.when(pendingOrder.getStatus()).thenReturn(OrderStatus.PENDING);
        Mockito.when(paidOrder.getStatus()).thenReturn(OrderStatus.COMPLETED);
    }
    
    @Test
    void givenOrder_whenSave_thenOngoingOrderCanBeFound() {
        orderRepository.save(pendingOrder);
        
        Optional<Order> result = orderRepository.findOngoingOrderFor(buyerId);
        
        assertTrue(result.isPresent());
        assertEquals(pendingOrder, result.get());
    }
    
    @Test
    void givenOnlyNonPendingOrders_whenFindOngoingOrderFor_thenReturnsEmpty() {
        orderRepository.save(paidOrder);
        
        Optional<Order> result = orderRepository.findOngoingOrderFor(buyerId);
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    void givenPendingAndNonPendingOrders_whenFindOngoingOrderFor_thenReturnsPendingOne() {
        orderRepository.save(paidOrder);
        orderRepository.save(pendingOrder);
        
        Optional<Order> result = orderRepository.findOngoingOrderFor(buyerId);
        
        assertTrue(result.isPresent());
        assertEquals(pendingOrder, result.get());
    }
    
}