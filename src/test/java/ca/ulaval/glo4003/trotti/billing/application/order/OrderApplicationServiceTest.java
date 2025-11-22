package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;

import java.util.Optional;


class OrderApplicationServiceTest {
	
    private static final Idul BUYER_ID = Idul.from("IDUL123");
    private static final ItemId ITEM_ID = ItemId.randomId();
	
	
	
	private OrderRepository orderRepository;
	private OrderAssembler	orderAssembler;
	private OrderItemFactory orderItemFactory;
	private PaymentMethodFactory paymentMethodFactory;
	private PaymentGateway paymentGateway;
	private EventBus eventBus;
	
	private OrderApplicationService orderApplicationService;
    
    @BeforeEach
    void setUp() {
		orderRepository = Mockito.mock(OrderRepository.class);
		orderAssembler = Mockito.mock(OrderAssembler.class);
		orderItemFactory = Mockito.mock(OrderItemFactory.class);
		paymentMethodFactory = Mockito.mock(PaymentMethodFactory.class);
		paymentGateway = Mockito.mock(PaymentGateway.class);
		eventBus = Mockito.mock(EventBus.class);
		orderApplicationService = new OrderApplicationService(
			orderRepository,
			orderAssembler,
			orderItemFactory,
			paymentMethodFactory,
			paymentGateway,
			eventBus);
	}

    @Test
    void givenNoOngoingOrder_whenAddItem_thenNewOrderCreatedAndSaved() {
		AddItemDto addItemDto = Mockito.mock(AddItemDto.class);
		Mockito.when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.empty());
		
		orderApplicationService.addItem(BUYER_ID, addItemDto);
		
		Mockito.verify(orderRepository).save(Mockito.any(Order.class));
    }

    @Test
    void givenOngoingOrder_whenAddItem_thenItemAddedAndOrderSaved() {
		AddItemDto addItemDto = Mockito.mock(AddItemDto.class);
        Order existingOrder = Mockito.spy(new Order(OrderId.randomId(), BUYER_ID));
        Mockito.when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));
		Mockito.when(orderItemFactory.create(addItemDto.maximumDailyTravelTime(), addItemDto.session(), addItemDto.billingFrequency())).thenReturn(Mockito.mock(RidePermitItem.class));
		
        orderApplicationService.addItem(BUYER_ID, addItemDto);
		
		Mockito.verify(existingOrder).add(Mockito.any(RidePermitItem.class));
        Mockito.verify(orderRepository).save(existingOrder);
    }

    @Test
    void givenOngoingOrderExists_whenGetOngoingOrder_thenReturnsDto() {
        Order existingOrder = new Order(OrderId.randomId(), BUYER_ID);
        Mockito.when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));
		
		orderApplicationService.getOngoingOrder(BUYER_ID);
		
		Mockito.verify(orderAssembler).assemble(existingOrder);
    }

    @Test
    void givenNoOngoingOrder_whenGetOngoingOrder_thenThrowsNotFoundException() {
        Mockito.when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.empty());
		
		Executable lookingForNonExistingOngoingOrder = () -> orderApplicationService.getOngoingOrder(BUYER_ID);
		
		Assertions.assertThrows(NotFoundException.class, lookingForNonExistingOngoingOrder);
    }

    @Test
    void givenOngoingOrder_whenRemoveItem_thenItemRemovedAndOrderSaved() {
        Order existingOrder = Mockito.spy(new Order(OrderId.randomId(), BUYER_ID));
		Mockito.when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));
		
		orderApplicationService.removeItem(BUYER_ID, ITEM_ID);
		
        Mockito.verify(existingOrder).remove(ITEM_ID);
		Mockito.verify(orderRepository).save(existingOrder);
    }

    @Test
    void givenOngoingOrder_whenRemoveAllItems_thenOrderClearedAndSaved() {
        
        Order existingOrder = Mockito.spy(new Order(OrderId.randomId(), BUYER_ID));
		Mockito.when(orderRepository.findOngoingOrderFor(BUYER_ID)).thenReturn(Optional.of(existingOrder));
		
        orderApplicationService.removeAllItems(BUYER_ID);
		
		Mockito.verify(existingOrder).clear();
		Mockito.verify(orderRepository).save(existingOrder);
    }
}