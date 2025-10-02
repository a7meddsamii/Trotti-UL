package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.application.order.mappers.TransactionMapper;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.NotificationService;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.Order;
import ca.ulaval.glo4003.trotti.domain.order.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.order.repository.PassRepository;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentMethodException;
import ca.ulaval.glo4003.trotti.domain.payment.services.InvoiceNotificationService;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.payment.services.TransactionNotificationService;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class OrderApplicationServiceTest {

    private static final String TRANSACTION_STRING = "Transaction Details";
    private static final String A_CVV = "123";

    private BuyerRepository buyerRepository;
    private PassRepository passRepository;
    private OrderFactory orderFactory;
    private PaymentService paymentService;
    private TransactionMapper transactionMapper;
    private NotificationService<Transaction> transactionNotificationService;
    private NotificationService<Invoice> invoiceNotificationService;
    private Buyer buyer;
    private CreditCard paymentMethod;
    private Transaction successfulTransaction;
    private Transaction failedTransaction;
    private TransactionDto transactionDto;
    private Order order;
    private Invoice invoice;
    private Idul idul;
    private Email email;
    private Money cartBalance;
    private Pass pass1;
    private Pass pass2;

    private OrderApplicationService orderApplicationService;
    private List<Pass> passes;
    private List<Pass> confirmedPasses;

    @BeforeEach
    void setup() {
        buyerRepository = Mockito.mock(BuyerRepository.class);
        passRepository = Mockito.mock(PassRepository.class);
        orderFactory = Mockito.mock(OrderFactory.class);
        paymentService = Mockito.mock(PaymentService.class);
        transactionMapper = Mockito.mock(TransactionMapper.class);
        transactionNotificationService = Mockito.mock(TransactionNotificationService.class);
        invoiceNotificationService = Mockito.mock(InvoiceNotificationService.class);
        buyer = Mockito.mock(Buyer.class);
        paymentMethod = Mockito.mock(CreditCard.class);
        successfulTransaction = Mockito.mock(Transaction.class);
        failedTransaction = Mockito.mock(Transaction.class);
        transactionDto = Mockito.mock(TransactionDto.class);
        order = Mockito.mock(Order.class);
        invoice = Mockito.mock(Invoice.class);
        idul = Mockito.mock(Idul.class);
        email = Mockito.mock(Email.class);
        cartBalance = Mockito.mock(Money.class);
        pass1 = Mockito.mock(Pass.class);
        pass2 = Mockito.mock(Pass.class);

        passes = Arrays.asList(pass1, pass2);
        confirmedPasses = Mockito.mock(List.class);

        orderApplicationService = new OrderApplicationService(buyerRepository, passRepository,
                orderFactory, paymentService, transactionMapper, transactionNotificationService,
                invoiceNotificationService);
    }

    @Test
    void givenSuccessfulTransaction_whenPlaceOrderFor_thenInvoiceAndTransactionNotificationServicesAreCalled() {
        mockSuccessfulTransaction();

        orderApplicationService.placeOrderFor(idul, A_CVV);

        Mockito.verify(invoiceNotificationService).notify(email, invoice);
        Mockito.verify(transactionNotificationService).notify(email, successfulTransaction);
    }

    @Test
    void givenFailedTransaction_whenPlaceOrderFor_thenOnlyTransactionNotificationServiceIsCalled() {
        mockFailedTransaction();

        orderApplicationService.placeOrderFor(idul, A_CVV);

        Mockito.verify(invoiceNotificationService, Mockito.never()).notify(Mockito.any(),
                Mockito.any());
        Mockito.verify(transactionNotificationService).notify(email, failedTransaction);
    }

    @Test
    void givenTransaction_whenPlaceOrderFor_thenTransactionMapperIsCalled() {
        mockFailedTransaction();

        orderApplicationService.placeOrderFor(idul, A_CVV);

        Mockito.verify(transactionMapper).toDto(failedTransaction);
    }

    @Test
    void givenNoPaymentMethod_whenPlaceOrderFor_thenThrowsException() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getPaymentMethod()).thenReturn(Optional.empty());

        Executable placeOrderAction = () -> orderApplicationService.placeOrderFor(idul, A_CVV);

        Assertions.assertThrows(InvalidPaymentMethodException.class, placeOrderAction);

        Mockito.verifyNoInteractions(paymentService);
        Mockito.verifyNoInteractions(orderFactory);
        Mockito.verifyNoInteractions(invoiceNotificationService);
        Mockito.verifyNoInteractions(transactionNotificationService);
    }

    @Test
    void givenSuccessfulTransaction_whenPlaceOrderFor_thenSavesPasses() {
        mockSuccessfulTransaction();
        Mockito.when(buyer.confirmCartPasses()).thenReturn(confirmedPasses);

        orderApplicationService.placeOrderFor(idul, A_CVV);

        Mockito.verify(passRepository).saveAll(confirmedPasses);
    }

    @Test
    void givenSuccessfulTransaction_whenPlaceOrderFor_thenUpdatesBuyer() {
        mockSuccessfulTransaction();
        Mockito.when(buyer.confirmCartPasses()).thenReturn(confirmedPasses);

        orderApplicationService.placeOrderFor(idul, A_CVV);

        Mockito.verify(buyerRepository).update(buyer);
    }

    @Test
    void givenFailedTransaction_whenPlaceOrderFor_thenDoesNotSavePasses() {
        mockFailedTransaction();

        orderApplicationService.placeOrderFor(idul, A_CVV);

        Mockito.verify(passRepository, Mockito.never()).saveAll(Mockito.anyList());
    }

    @Test
    void givenFailedTransaction_whenPlaceOrderFor_thenDoesNotUpdateBuyer() {
        mockFailedTransaction();

        orderApplicationService.placeOrderFor(idul, A_CVV);

        Mockito.verify(buyerRepository, Mockito.never()).update(Mockito.any());
    }

    @Test
    void givenSuccessfulTransaction_whenPlaceOrderFor_thenOrderIsCreated() {
        mockSuccessfulTransaction();
        Mockito.when(buyer.confirmCartPasses()).thenReturn(confirmedPasses);

        orderApplicationService.placeOrderFor(idul, A_CVV);

        Mockito.verify(orderFactory).create(idul, confirmedPasses);
    }

    private void mockSuccessfulTransaction() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        Mockito.when(buyer.getCartBalance()).thenReturn(cartBalance);
        Mockito.when(buyer.getEmail()).thenReturn(email);
        Mockito.when(buyer.getCartPasses()).thenReturn(passes);
        Mockito.when(buyer.getIdul()).thenReturn(idul);
        Mockito.when(paymentService.process(paymentMethod, cartBalance, A_CVV))
                .thenReturn(successfulTransaction);
        Mockito.when(transactionMapper.toDto(successfulTransaction)).thenReturn(transactionDto);
        Mockito.when(successfulTransaction.isSuccessful()).thenReturn(true);
        Mockito.when(successfulTransaction.toString()).thenReturn(TRANSACTION_STRING);
        Mockito.when(orderFactory.create(Mockito.any(Idul.class), Mockito.anyList()))
                .thenReturn(order);
        Mockito.when(order.generateInvoice()).thenReturn(invoice);
    }

    private void mockFailedTransaction() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        Mockito.when(buyer.getCartBalance()).thenReturn(cartBalance);
        Mockito.when(buyer.getEmail()).thenReturn(email);
        Mockito.when(buyer.getCartPasses()).thenReturn(passes);
        Mockito.when(buyer.getIdul()).thenReturn(idul);
        Mockito.when(paymentService.process(paymentMethod, cartBalance, A_CVV))
                .thenReturn(failedTransaction);
        Mockito.when(transactionMapper.toDto(failedTransaction)).thenReturn(transactionDto);
        Mockito.when(failedTransaction.isSuccessful()).thenReturn(false);
        Mockito.when(failedTransaction.toString()).thenReturn(TRANSACTION_STRING);
        Mockito.when(orderFactory.create(Mockito.any(Idul.class), Mockito.anyList()))
                .thenReturn(order);
        Mockito.when(order.generateInvoice()).thenReturn(invoice);
    }
}
