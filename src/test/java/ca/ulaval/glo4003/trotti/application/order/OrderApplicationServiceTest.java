// File: src/test/java/ca/ulaval/glo4003/trotti/application/order/OrderApplicationServiceTest.java
package ca.ulaval.glo4003.trotti.application.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.trotti.application.order.dto.PaymentInfoDto;
import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.application.order.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.communication.services.NotificationService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions.InvalidPaymentMethodException;
import ca.ulaval.glo4003.trotti.domain.commons.payment.exceptions.PaymentException;
import ca.ulaval.glo4003.trotti.domain.commons.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.Transaction;
import ca.ulaval.glo4003.trotti.domain.order.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Order;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.domain.order.exceptions.CartException;
import ca.ulaval.glo4003.trotti.domain.order.factories.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.domain.order.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class OrderApplicationServiceTest {

    private static final String VALID_CVV = "123";
    private static final String VALID_CARD_NUMBER = "4111111111111111";
    private static final String VALID_CARD_HOLDER = "John Doe";
    private static final String ALTERNATE_CVV = "321";
    private static final String NO_SAVED_PAYMENT_METHOD_MSG = "No saved payment method";
    private static final String INVALID_PAYMENT_METHOD_MSG =
            "Invalid or incomplete payment method information";

    private BuyerRepository buyerRepository;
    private PassRepository passRepository;
    private PaymentMethodFactory paymentMethodFactory;
    private OrderFactory orderFactory;
    private PaymentService paymentService;
    private TransactionMapper transactionMapper;
    private NotificationService<Transaction> transactionNotificationService;
    private NotificationService<Invoice> invoiceNotificationService;
    private OrderApplicationService orderApplicationService;

    private Buyer buyer;
    private CreditCard creditCard;
    private Transaction successfulTransaction;
    private Transaction failedTransaction;
    private Order order;
    private Invoice invoice;
    private PaymentInfoDto paymentInfoDto;
    private Idul idul;
    private Money cartBalance;
    private List<Pass> passes;
    private TransactionDto transactionDto;

    @BeforeEach
    void setup() {
        buyerRepository = mock(BuyerRepository.class);
        passRepository = mock(PassRepository.class);
        paymentMethodFactory = mock(PaymentMethodFactory.class);
        orderFactory = mock(OrderFactory.class);
        paymentService = mock(PaymentService.class);
        transactionMapper = mock(TransactionMapper.class);
        transactionNotificationService = mock(NotificationService.class);
        invoiceNotificationService = mock(NotificationService.class);

        orderApplicationService = new OrderApplicationService(buyerRepository, passRepository,
                paymentMethodFactory, orderFactory, paymentService, transactionMapper,
                transactionNotificationService, invoiceNotificationService);

        buyer = mock(Buyer.class);
        creditCard = mock(CreditCard.class);
        successfulTransaction = mock(Transaction.class);
        failedTransaction = mock(Transaction.class);
        order = mock(Order.class);
        invoice = mock(Invoice.class);
        paymentInfoDto = mock(PaymentInfoDto.class);
        idul = mock(Idul.class);
        cartBalance = mock(Money.class);
        passes = Arrays.asList(mock(Pass.class), mock(Pass.class));
        transactionDto = mock(TransactionDto.class);

        when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        when(buyer.hasEmptyCart()).thenReturn(false);
        when(buyer.getCartBalance()).thenReturn(cartBalance);
        when(buyer.getCartPasses()).thenReturn(passes);
        when(paymentInfoDto.cvv()).thenReturn(VALID_CVV);
        when(transactionMapper.toDto(any(Transaction.class))).thenReturn(transactionDto);
    }

    @Test
    void givenEmptyCart_whenPlaceOrderFor_thenThrowCartException() {
        when(buyer.hasEmptyCart()).thenReturn(true);
        Executable action = () -> orderApplicationService.placeOrderFor(idul, paymentInfoDto);
        assertThrows(CartException.class, action);
    }

    @Test
    void givenExistingPaymentMethod_andSuccessfulTransaction_whenPlaceOrderFor_thenFinalizeOrderAndNotify() {
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(creditCard));
        when(paymentService.process(creditCard, cartBalance, VALID_CVV)).thenReturn(successfulTransaction);
        when(successfulTransaction.isSuccessful()).thenReturn(true);
        when(buyer.confirmCartPasses()).thenReturn(passes);
        when(orderFactory.create(buyer.getIdul(), passes)).thenReturn(order);
        when(order.generateInvoice()).thenReturn(invoice);

        TransactionDto result = orderApplicationService.placeOrderFor(idul, paymentInfoDto);

        verify(invoiceNotificationService).notify(buyer.getEmail(), invoice);
        verify(transactionNotificationService).notify(buyer.getEmail(), successfulTransaction);
        verify(buyerRepository).update(buyer);
        verify(passRepository).saveAll(passes);
        assertEquals(transactionDto, result);
    }

    @Test
    void givenNoPaymentMethodByBuyerAndCompletePaymentInfo_whenPlaceOrderFor_thenCreatesPaymentMethodAndFinalizeOrder() {
        when(buyer.getPaymentMethod()).thenReturn(Optional.empty());
        when(paymentInfoDto.cardNumber()).thenReturn(VALID_CARD_NUMBER);
        when(paymentInfoDto.cardHolderName()).thenReturn(VALID_CARD_HOLDER);
        when(paymentInfoDto.expirationDate()).thenReturn(YearMonth.now().plusMonths(1));

        CreditCard newCard = mock(CreditCard.class);
        when(paymentMethodFactory.createCreditCard(
                VALID_CARD_NUMBER,
                VALID_CARD_HOLDER,
                paymentInfoDto.expirationDate(),
                VALID_CVV))
                .thenReturn(newCard);

        when(paymentService.process(newCard, cartBalance, VALID_CVV)).thenReturn(successfulTransaction);
        when(successfulTransaction.isSuccessful()).thenReturn(true);
        when(buyer.confirmCartPasses()).thenReturn(passes);
        when(orderFactory.create(buyer.getIdul(), passes)).thenReturn(order);
        when(order.generateInvoice()).thenReturn(invoice);

        TransactionDto result = orderApplicationService.placeOrderFor(idul, paymentInfoDto);

        verify(buyer).updatePaymentMethod(newCard);
        verify(invoiceNotificationService).notify(buyer.getEmail(), invoice);
        verify(transactionNotificationService).notify(buyer.getEmail(), successfulTransaction);
        verify(buyerRepository).update(buyer);
        verify(passRepository).saveAll(passes);
        assertEquals(transactionDto, result);
    }

    @Test
    void givenSuccessfulTransaction_whenPlaceOrderFor_thenFinalizeOrderIsCalled() {
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(creditCard));
        when(paymentService.process(creditCard, cartBalance, VALID_CVV)).thenReturn(successfulTransaction);
        when(successfulTransaction.isSuccessful()).thenReturn(true);
        when(buyer.confirmCartPasses()).thenReturn(passes);
        when(orderFactory.create(buyer.getIdul(), passes)).thenReturn(order);
        when(order.generateInvoice()).thenReturn(invoice);

        orderApplicationService.placeOrderFor(idul, paymentInfoDto);

        verify(buyerRepository).update(buyer);
        verify(passRepository).saveAll(passes);
    }

    @Test
    void givenFailedTransaction_whenPlaceOrderFor_thenOnlyTransactionNotificationIsCalled_andNoOrderFinalization() {
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(creditCard));
        when(paymentService.process(creditCard, cartBalance, VALID_CVV)).thenReturn(failedTransaction);
        when(failedTransaction.isSuccessful()).thenReturn(false);

        Executable action = () -> orderApplicationService.placeOrderFor(idul, paymentInfoDto);

        assertThrows(PaymentException.class, action);
        verify(invoiceNotificationService, never()).notify(any(), any());
        verify(buyerRepository, never()).update(buyer);
        verify(passRepository, never()).saveAll(any());
        verify(transactionNotificationService).notify(buyer.getEmail(), failedTransaction);
    }

    @Test
    void givenIncompletePaymentInfoWithOnlyCvv_andSavedPaymentMethodPresent_thenPlaceOrderForUsesSavedPaymentMethod() {
        when(paymentInfoDto.cardNumber()).thenReturn("");
        when(paymentInfoDto.cardHolderName()).thenReturn("");
        when(paymentInfoDto.expirationDate()).thenReturn(null);
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(creditCard));
        when(paymentService.process(creditCard, cartBalance, VALID_CVV)).thenReturn(successfulTransaction);
        when(successfulTransaction.isSuccessful()).thenReturn(true);
        when(buyer.confirmCartPasses()).thenReturn(passes);
        when(orderFactory.create(buyer.getIdul(), passes)).thenReturn(order);
        when(order.generateInvoice()).thenReturn(invoice);

        TransactionDto result = orderApplicationService.placeOrderFor(idul, paymentInfoDto);

        verify(paymentMethodFactory, never()).createCreditCard(anyString(), anyString(), any(), anyString());
        assertEquals(transactionDto, result);
    }

    @Test
    void givenIncompletePaymentInfoWithOnlyCvv_andNoSavedPaymentMethod_thenPlaceOrderForThrowsException() {
        when(paymentInfoDto.cardNumber()).thenReturn("");
        when(paymentInfoDto.cardHolderName()).thenReturn("");
        when(paymentInfoDto.expirationDate()).thenReturn(null);
        when(buyer.getPaymentMethod()).thenReturn(Optional.empty());

        Executable action = () -> orderApplicationService.placeOrderFor(idul, paymentInfoDto);
        InvalidPaymentMethodException exception = assertThrows(InvalidPaymentMethodException.class, action);
        assertEquals(NO_SAVED_PAYMENT_METHOD_MSG, exception.getMessage());
    }
}
