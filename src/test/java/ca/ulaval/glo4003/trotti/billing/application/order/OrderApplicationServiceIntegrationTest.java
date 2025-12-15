package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.factory.OrderItemFactory;
import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.repository.InMemoryOrderRepository;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;

class OrderApplicationServiceIntegrationTest {

    private static final Idul BUYER_ID = Idul.from("IDUL123");

    private OrderRepository orderRepository;
    private OrderAssembler orderAssembler;
    private OrderItemFactory orderItemFactory;
    private PaymentMethodFactory paymentMethodFactory;
    private PaymentGateway paymentGateway;
    private EventBus eventBus;
    private AddItemDto addItemDto;

    private OrderApplicationService orderApplicationService;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        orderAssembler = new OrderAssembler();
        orderItemFactory = new OrderItemFactory();
        eventBus = Mockito.mock(EventBus.class);

        paymentMethodFactory = Mockito.mock(PaymentMethodFactory.class);
        paymentGateway = Mockito.mock(PaymentGateway.class);

        addItemDto = createAddItemDto();

        orderApplicationService = new OrderApplicationService(orderRepository, orderAssembler,
                orderItemFactory, paymentMethodFactory, paymentGateway, eventBus);
    }

    @Test
    void givenNoOngoingOrder_whenAddItem_thenNewOrderCreatedAndSaved() {
        orderApplicationService.addItem(BUYER_ID, addItemDto);

        Optional<Order> savedOrder = orderRepository.findOngoingOrderFor(BUYER_ID);
        Assertions.assertTrue(savedOrder.isPresent());
        Assertions.assertEquals(BUYER_ID, savedOrder.get().getBuyerId());
    }

    @Test
    void givenOngoingOrder_whenAddItem_thenItemAddedAndOrderSaved() {
        Order existingOrder = new Order(OrderId.randomId(), BUYER_ID);
        orderRepository.save(existingOrder);
        int initialItemCount = existingOrder.getItems().size();

        orderApplicationService.addItem(BUYER_ID, addItemDto);

        Optional<Order> updatedOrder = orderRepository.findOngoingOrderFor(BUYER_ID);
        Assertions.assertTrue(updatedOrder.isPresent());
        Assertions.assertEquals(initialItemCount + 1, updatedOrder.get().getItems().size());
    }

    @Test
    void givenOngoingOrderExists_whenGetOngoingOrder_thenReturnsDto() {
        Order existingOrder = new Order(OrderId.randomId(), BUYER_ID);
        orderRepository.save(existingOrder);

        var result = orderApplicationService.getOngoingOrder(BUYER_ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(BUYER_ID, result.buyerId());
    }

    @Test
    void givenNoOngoingOrder_whenGetOngoingOrder_thenThrowsException() {
        Executable lookingForNonExistingOngoingOrder =
                () -> orderApplicationService.getOngoingOrder(BUYER_ID);

        Assertions.assertThrows(NotFoundException.class, lookingForNonExistingOngoingOrder);
    }

    @Test
    void givenOngoingOrder_whenRemoveItem_thenItemRemovedAndOrderSaved() {
        Order existingOrder = new Order(OrderId.randomId(), BUYER_ID);
        var item = orderItemFactory.create(addItemDto.maximumDailyTravelTime(),
                addItemDto.session(), addItemDto.billingFrequency());
        existingOrder.add(item);
        orderRepository.save(existingOrder);
        int initialItemCount = existingOrder.getItems().size();

        orderApplicationService.removeItem(BUYER_ID, item.getItemId());

        Optional<Order> updatedOrder = orderRepository.findOngoingOrderFor(BUYER_ID);
        Assertions.assertTrue(updatedOrder.isPresent());
        Assertions.assertEquals(initialItemCount - 1, updatedOrder.get().getItems().size());
    }

    @Test
    void givenOngoingOrder_whenRemoveAllItems_thenOrderClearedAndSaved() {
        Order existingOrder = new Order(OrderId.randomId(), BUYER_ID);
        var item = orderItemFactory.create(addItemDto.maximumDailyTravelTime(),
                addItemDto.session(), addItemDto.billingFrequency());
        existingOrder.add(item);
        orderRepository.save(existingOrder);

        orderApplicationService.removeAllItems(BUYER_ID);

        Optional<Order> updatedOrder = orderRepository.findOngoingOrderFor(BUYER_ID);
        Assertions.assertTrue(updatedOrder.isPresent());
        Assertions.assertEquals(0, updatedOrder.get().getItems().size());
    }

    private AddItemDto createAddItemDto() {
        return new AddItemDto(MaximumDailyTravelTime.baseTravelTime(),
                new Session(Semester.FALL, LocalDate.now(), LocalDate.now().plusMonths(4)),
                BillingFrequency.MONTHLY);
    }
}
