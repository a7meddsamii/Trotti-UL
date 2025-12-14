package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.ConfirmOrderDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.FreeRidePermitItemGrantDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.RidePermitItem;
import ca.ulaval.glo4003.trotti.billing.domain.order.factory.OrderItemFactory;
import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.*;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;
import ca.ulaval.glo4003.trotti.billing.domain.payment.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentReceipt;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.TransactionId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethod;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethodType;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.OrderPlacedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.RidePermitItemSnapshot;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.payment.TransactionCompletedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class OrderApplicationServiceTest {

    private static final Idul VALID_BUYER_ID = Idul.from("buyer123");
    private static final ItemId VALID_ITEM_ID = ItemId.randomId();
    private static final OrderId VALID_ORDER_ID_VALUE = OrderId.randomId();
    private static final String PAYMENT_SUCCESS_DESCRIPTION = "Payment successful";
    private static final String PAYMENT_FAILURE_DESCRIPTION = "Insufficient funds";

    private OrderRepository orderRepository;
    private OrderAssembler orderAssembler;
    private OrderItemFactory orderItemFactory;
    private PaymentMethodFactory paymentMethodFactory;
    private PaymentGateway paymentGateway;
    private EventBus eventBus;
    private OrderId orderId;

    private OrderApplicationService orderApplicationService;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderAssembler = Mockito.mock(OrderAssembler.class);
        orderItemFactory = Mockito.mock(OrderItemFactory.class);
        paymentMethodFactory = Mockito.mock(PaymentMethodFactory.class);
        paymentGateway = Mockito.mock(PaymentGateway.class);
        eventBus = Mockito.mock(EventBus.class);
        orderApplicationService = new OrderApplicationService(orderRepository, orderAssembler,
                orderItemFactory, paymentMethodFactory, paymentGateway, eventBus);
    }

    @Test
    void givenExistingCart_whenAddingItem_thenReturnsUpdatedOrder() {
        AddItemDto addItemDto = createAddItemDto();
        Order existingOrder = createOrder();
        OrderDto expectedOrderDto = Mockito.mock(OrderDto.class);
        RidePermitItem item = Mockito.mock(RidePermitItem.class);

        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.of(existingOrder));
        Mockito.when(orderItemFactory.create(Mockito.any(MaximumDailyTravelTime.class),
                Mockito.any(Session.class), Mockito.any(BillingFrequency.class))).thenReturn(item);
        Mockito.when(orderAssembler.assemble(Mockito.any(Order.class)))
                .thenReturn(expectedOrderDto);

        OrderDto result = orderApplicationService.addItem(VALID_BUYER_ID, addItemDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedOrderDto, result);
    }

    @Test
    void givenNoExistingCart_whenAddingItem_thenCreatesNewCartAndReturnsOrder() {
        AddItemDto addItemDto = createAddItemDto();
        OrderDto expectedOrderDto = Mockito.mock(OrderDto.class);
        RidePermitItem item = Mockito.mock(RidePermitItem.class);

        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.empty());
        Mockito.when(orderItemFactory.create(Mockito.any(MaximumDailyTravelTime.class),
                Mockito.any(Session.class), Mockito.any(BillingFrequency.class))).thenReturn(item);
        Mockito.when(orderAssembler.assemble(Mockito.any(Order.class)))
                .thenReturn(expectedOrderDto);

        OrderDto result = orderApplicationService.addItem(VALID_BUYER_ID, addItemDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedOrderDto, result);
    }

    @Test
    void givenExistingCart_whenRemovingItem_thenReturnsUpdatedOrder() {
        Order existingOrder = createOrder();
        OrderDto expectedOrderDto = Mockito.mock(OrderDto.class);

        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.of(existingOrder));
        Mockito.when(orderAssembler.assemble(existingOrder)).thenReturn(expectedOrderDto);

        OrderDto result = orderApplicationService.removeItem(VALID_BUYER_ID, VALID_ITEM_ID);

        Assertions.assertEquals(expectedOrderDto, result);
    }

    @Test
    void givenNoCart_whenRemovingItem_thenThrowsException() {
        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(NotFoundException.class,
                () -> orderApplicationService.removeItem(VALID_BUYER_ID, VALID_ITEM_ID));

        Assertions.assertNotNull(exception);
    }

    @Test
    void givenExistingCart_whenClearingCart_thenReturnsEmptyOrder() {
        Order existingOrder = createOrder();
        OrderDto emptyOrderDto = Mockito.mock(OrderDto.class);

        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.of(existingOrder));
        Mockito.when(orderAssembler.assemble(existingOrder)).thenReturn(emptyOrderDto);

        OrderDto result = orderApplicationService.removeAllItems(VALID_BUYER_ID);

        Assertions.assertEquals(emptyOrderDto, result);
    }

    @Test
    void givenNoCart_whenClearingCart_thenThrowsException() {
        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(NotFoundException.class,
                () -> orderApplicationService.removeAllItems(VALID_BUYER_ID));

        Assertions.assertNotNull(exception);
    }

    @Test
    void givenExistingCart_whenGettingCart_thenReturnsCurrentOrder() {
        Order existingOrder = createOrder();
        OrderDto expectedOrderDto = Mockito.mock(OrderDto.class);

        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.of(existingOrder));
        Mockito.when(orderAssembler.assemble(existingOrder)).thenReturn(expectedOrderDto);

        OrderDto result = orderApplicationService.getOngoingOrder(VALID_BUYER_ID);

        Assertions.assertEquals(expectedOrderDto, result);
    }

    @Test
    void givenNoCart_whenGettingCart_thenThrowsException() {
        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(NotFoundException.class,
                () -> orderApplicationService.getOngoingOrder(VALID_BUYER_ID));

        Assertions.assertNotNull(exception);
    }

    @Test
    void givenExistingPaymentMethod_whenCheckoutWithSuccessfulPayment_thenOrderConfirmedAndEventsPublished() {
        Order existingOrder = createOrder();
        ConfirmOrderDto confirmDto = createConfirmOrderDto();
        PaymentMethod existingPaymentMethod = Mockito.mock(PaymentMethod.class);
        PaymentReceipt successReceipt = createSuccessfulPaymentReceipt();
        List<RidePermitItemSnapshot> snapshots =
                List.of(Mockito.mock(RidePermitItemSnapshot.class));

        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.of(existingOrder));
        Mockito.when(paymentGateway.getPaymentMethod(VALID_BUYER_ID))
                .thenReturn(Optional.of(existingPaymentMethod));
        Mockito.when(paymentGateway.pay(Mockito.any())).thenReturn(successReceipt);
        Mockito.when(orderAssembler.assembleRidePermitItemSnapshots(Mockito.any()))
                .thenReturn(snapshots);

        orderApplicationService.confirm(VALID_BUYER_ID, confirmDto);

        ArgumentCaptor<OrderPlacedEvent> orderEventCaptor =
                ArgumentCaptor.forClass(OrderPlacedEvent.class);
        Mockito.verify(eventBus).publish(orderEventCaptor.capture());

        OrderPlacedEvent event = orderEventCaptor.getValue();
        Assertions.assertEquals(VALID_BUYER_ID, event.getIdul());
        Assertions.assertFalse(event.getRidePermitItems().isEmpty());
    }

    @Test
    void givenNoPaymentMethod_whenCheckoutWithSuccessfulPayment_thenCreatesPaymentMethodAndOrderConfirmed() {
        Order existingOrder = createOrder();
        ConfirmOrderDto confirmDto = createConfirmOrderDto();
        PaymentMethod newPaymentMethod = Mockito.mock(PaymentMethod.class);
        PaymentReceipt successReceipt = createSuccessfulPaymentReceipt();
        List<RidePermitItemSnapshot> snapshots =
                List.of(Mockito.mock(RidePermitItemSnapshot.class));

        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.of(existingOrder));
        Mockito.when(paymentGateway.getPaymentMethod(VALID_BUYER_ID)).thenReturn(Optional.empty());
        Mockito.when(paymentMethodFactory.createCreditCard(confirmDto.creditCardNumber(),
                confirmDto.cardHolderName(), confirmDto.expiryDate())).thenReturn(newPaymentMethod);
        Mockito.when(paymentGateway.pay(Mockito.any())).thenReturn(successReceipt);
        Mockito.when(orderAssembler.assembleRidePermitItemSnapshots(Mockito.any()))
                .thenReturn(snapshots);

        orderApplicationService.confirm(VALID_BUYER_ID, confirmDto);

        Mockito.verify(paymentMethodFactory).createCreditCard(confirmDto.creditCardNumber(),
                confirmDto.cardHolderName(), confirmDto.expiryDate());

        ArgumentCaptor<OrderPlacedEvent> orderEventCaptor =
                ArgumentCaptor.forClass(OrderPlacedEvent.class);
        Mockito.verify(eventBus).publish(orderEventCaptor.capture());

        OrderPlacedEvent event = orderEventCaptor.getValue();
        Assertions.assertEquals(VALID_BUYER_ID, event.getIdul());
    }

    @Test
    void givenFailedPayment_whenCheckout_thenOrderNotConfirmedButTransactionEventPublished() {
        Order existingOrder = createOrder();
        ConfirmOrderDto confirmDto = createConfirmOrderDto();
        PaymentMethod paymentMethod = Mockito.mock(PaymentMethod.class);
        PaymentReceipt failureReceipt = createFailedPaymentReceipt();

        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.of(existingOrder));
        Mockito.when(paymentGateway.getPaymentMethod(VALID_BUYER_ID))
                .thenReturn(Optional.of(paymentMethod));
        Mockito.when(paymentGateway.pay(Mockito.any())).thenReturn(failureReceipt);

        orderApplicationService.confirm(VALID_BUYER_ID, confirmDto);

        ArgumentCaptor<TransactionCompletedEvent> txEventCaptor =
                ArgumentCaptor.forClass(TransactionCompletedEvent.class);
        Mockito.verify(eventBus).publish(txEventCaptor.capture());
        Mockito.verify(eventBus, Mockito.never()).publish(Mockito.any(OrderPlacedEvent.class));

        TransactionCompletedEvent txEvent = txEventCaptor.getValue();
        Assertions.assertEquals("failed", txEvent.getTransactionStatus());
        Assertions.assertTrue(
                txEvent.getTransactionDescription().contains(PAYMENT_FAILURE_DESCRIPTION),
                "Description should contain: " + PAYMENT_FAILURE_DESCRIPTION);
    }

    @Test
    void givenNoCart_whenCheckout_thenThrowsException() {
        ConfirmOrderDto confirmDto = createConfirmOrderDto();
        Mockito.when(orderRepository.findOngoingOrderFor(VALID_BUYER_ID))
                .thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(NotFoundException.class,
                () -> orderApplicationService.confirm(VALID_BUYER_ID, confirmDto));

        Assertions.assertNotNull(exception);
    }

    @Test
    void whenGrantingFreeRidePermit_thenAutoConfirmedOrderCreatedAndEventPublished() {
        Session session = createSession();
        FreeRidePermitItemGrantDto grantDto =
                new FreeRidePermitItemGrantDto(VALID_BUYER_ID, session);
        RidePermitItem freeItem = Mockito.mock(RidePermitItem.class);
        List<RidePermitItemSnapshot> snapshots =
                List.of(Mockito.mock(RidePermitItemSnapshot.class));

        Mockito.when(orderItemFactory.create(Mockito.any(Session.class))).thenReturn(freeItem);
        Mockito.when(orderAssembler.assembleRidePermitItemSnapshots(Mockito.any()))
                .thenReturn(snapshots);

        orderApplicationService.grantFreeRidePermitItem(grantDto);

        ArgumentCaptor<OrderPlacedEvent> eventCaptor =
                ArgumentCaptor.forClass(OrderPlacedEvent.class);
        Mockito.verify(eventBus).publish(eventCaptor.capture());

        OrderPlacedEvent event = eventCaptor.getValue();
        Assertions.assertEquals(VALID_BUYER_ID, event.getIdul());
        Assertions.assertFalse(event.getRidePermitItems().isEmpty());
    }

    private AddItemDto createAddItemDto() {
        return new AddItemDto(MaximumDailyTravelTime.from(Duration.ofMinutes(30)), createSession(),
                BillingFrequency.MONTHLY);
    }

    private ConfirmOrderDto createConfirmOrderDto() {
        return new ConfirmOrderDto(PaymentMethodType.CREDIT_CARD, "4111111111111111", "John Doe",
                YearMonth.of(2026, 12));
    }

    private Session createSession() {
        return new Session(Semester.WINTER, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 4, 30));
    }

    private Order createOrder() {
        return new Order(VALID_ORDER_ID_VALUE, VALID_BUYER_ID);
    }

    private PaymentReceipt createSuccessfulPaymentReceipt() {
        return PaymentReceipt.of(TransactionId.randomId(), orderId, Money.zeroCad(), true,
                PAYMENT_SUCCESS_DESCRIPTION);
    }

    private PaymentReceipt createFailedPaymentReceipt() {
        return PaymentReceipt.of(TransactionId.randomId(), orderId, Money.zeroCad(), false,
                PAYMENT_FAILURE_DESCRIPTION);
    }
}
