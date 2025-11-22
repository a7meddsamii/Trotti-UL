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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderApplicationServiceTest {

    // Constants (only those reused across multiple tests)
    private static final Idul BUYER_ID = new Idul("IDUL123");
    private static final ItemId ITEM_ID = ItemId.randomId();

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderAssembler orderAssembler;
    @Mock
    private OrderItemFactory orderItemFactory;

    @Mock
    private RidePermitItem ridePermitItem;
    @Mock
    private OrderDto orderDto;
    @Mock
    private AddItemDto addItemDto;
    @Mock
    private ConfirmOrderDto confirmOrderDto;

    @InjectMocks
    private OrderApplicationService orderApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(orderItemFactory.create(any(), any(), any())).thenReturn(ridePermitItem);
        when(orderAssembler.assemble(any(Order.class))).thenReturn(orderDto);
    }

    @Test
    void givenNoOngoingOrder_whenAddItem_thenNewOrderCreated() {
        when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.empty());

        // Act
        OrderDto result = orderApplicationService.addItem(BUYER_ID, addItemDto);

        
        verify(orderItemFactory).create(any(), any(), any());
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        verify(orderAssembler).assemble(captor.getValue());
        assertThat(captor.getValue()).isNotNull();
    }

    @Test
    void givenOngoingOrder_whenAddItem_thenItemAddedAndOrderSavedAndAssembled() {
        // Arrange
        Order existingOrder = spy(new Order(OrderId.randomId(), BUYER_ID));
        when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));

        // Act
        OrderDto result = orderApplicationService.addItem(BUYER_ID, addItemDto);

        // Assert
        assertThat(result).isSameAs(orderDto);
        verify(existingOrder).add(ridePermitItem);
        verify(orderRepository).save(existingOrder);
        verify(orderAssembler).assemble(existingOrder);
    }

    @Test
    void givenOngoingOrderExists_whenGetOngoingOrder_thenAssemblerReturnsDto() {
        // Arrange
        Order existingOrder = new Order(OrderId.randomId(), BUYER_ID);
        when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));

        // Act
        OrderDto result = orderApplicationService.getOngoingOrder(BUYER_ID);

        // Assert
        assertThat(result).isSameAs(orderDto);
        verify(orderRepository).findOngoingOrderFor(BUYER_ID);
        verify(orderAssembler).assemble(existingOrder);
    }

    @Test
    void givenNoOngoingOrder_whenGetOngoingOrder_thenThrowsNotFoundException() {
        // Arrange
        when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> orderApplicationService.getOngoingOrder(BUYER_ID))
                .isInstanceOf(NotFoundException.class);
        verify(orderRepository).findOngoingOrderFor(BUYER_ID);
        verify(orderAssembler, never()).assemble(any());
    }

    @Test
    void givenOngoingOrder_whenRemoveItem_thenItemRemovedAndOrderSavedAndAssembled() {
        // Arrange
        Order existingOrder = spy(new Order(OrderId.randomId(), BUYER_ID));
        when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));

        // Act
        OrderDto result = orderApplicationService.removeItem(BUYER_ID, ITEM_ID);

        // Assert
        assertThat(result).isSameAs(orderDto);
        verify(existingOrder).remove(ITEM_ID);
        verify(orderRepository).save(existingOrder);
        verify(orderAssembler).assemble(existingOrder);
    }

    @Test
    void givenOngoingOrder_whenRemoveAllItems_thenOrderClearedAndSavedAndAssembled() {
        // Arrange
        Order existingOrder = spy(new Order(OrderId.randomId(), BUYER_ID));
        when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));

        // Act
        OrderDto result = orderApplicationService.removeAllItems(BUYER_ID);

        // Assert
        assertThat(result).isSameAs(orderDto);
        verify(existingOrder).clear();
        verify(orderRepository).save(existingOrder);
        verify(orderAssembler).assemble(existingOrder);
    }

    @Test
    void givenOngoingOrder_whenConfirm_thenOrderSaved() {
        // Arrange
        Order existingOrder = new Order(OrderId.randomId(), BUYER_ID);
        when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));

        // Act
        orderApplicationService.confirm(BUYER_ID, confirmOrderDto);

        // Assert
        verify(orderRepository).save(existingOrder);
        verify(orderRepository).findOngoingOrderFor(BUYER_ID);
        // No assembler invocation in confirm
        verify(orderAssembler, never()).assemble(any());
    }
}