package ca.ulaval.glo4003.trotti.application.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Invoice;
import ca.ulaval.glo4003.trotti.domain.order.Order;
import ca.ulaval.glo4003.trotti.domain.order.OrderFactory;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.domain.payment.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderApplicationServiceTest {

    private static final String FORMATTED_INVOICE = "Formatted Invoice Content";
    private static final String TRANSACTION_STRING = "Transaction Details";

    private BuyerRepository buyerRepository;
    private OrderFactory orderFactory;
    private PaymentService paymentService;
    private EmailService emailService;
    private InvoiceFormatService<String> invoiceFormatService;
    private Buyer buyer;
    private PaymentMethod paymentMethod;
    private Transaction successfulTransaction;
    private Transaction failedTransaction;
    private Order order;
    private Invoice invoice;
    private Idul idul;
    private Email email;
    private Money cartBalance;
    private Pass pass1;
    private Pass pass2;

    private OrderApplicationService orderApplicationService;
    private List<Pass> passes;

    @BeforeEach
    void setUp() {
        buyerRepository = Mockito.mock(BuyerRepository.class);
        orderFactory = Mockito.mock(OrderFactory.class);
        paymentService = Mockito.mock(PaymentService.class);
        emailService = Mockito.mock(EmailService.class);
        invoiceFormatService = Mockito.mock(InvoiceFormatService.class);
        buyer = Mockito.mock(Buyer.class);
        paymentMethod = Mockito.mock(PaymentMethod.class);
        successfulTransaction = Mockito.mock(Transaction.class);
        failedTransaction = Mockito.mock(Transaction.class);
        order = Mockito.mock(Order.class);
        invoice = Mockito.mock(Invoice.class);
        idul = Mockito.mock(Idul.class);
        email = Mockito.mock(Email.class);
        cartBalance = Mockito.mock(Money.class);
        pass1 = Mockito.mock(Pass.class);
        pass2 = Mockito.mock(Pass.class);

        orderApplicationService = new OrderApplicationService(buyerRepository, orderFactory,
                paymentService, emailService, invoiceFormatService);
        passes = Arrays.asList(pass1, pass2);
    }

    @Test
    void givenSuccessfulTransaction_whenPlaceOrderFor_thenSendsEmails() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        Mockito.when(buyer.getCartBalance()).thenReturn(cartBalance);
        Mockito.when(buyer.getEmail()).thenReturn(email);
        Mockito.when(buyer.getCartPasses()).thenReturn(passes);
        Mockito.when(paymentService.process(paymentMethod, cartBalance))
                .thenReturn(successfulTransaction);
        Mockito.when(successfulTransaction.isFailed()).thenReturn(false);
        Mockito.when(successfulTransaction.toString()).thenReturn(TRANSACTION_STRING);
        Mockito.when(orderFactory.create(idul, passes)).thenReturn(order);
        Mockito.when(order.generateInvoice()).thenReturn(invoice);
        Mockito.when(invoiceFormatService.format(invoice)).thenReturn(FORMATTED_INVOICE);

        Transaction result = orderApplicationService.placeOrderFor(idul);

        Assertions.assertEquals(successfulTransaction, result);
        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(paymentService).process(paymentMethod, cartBalance);
        Mockito.verify(orderFactory).create(idul, passes);
        Mockito.verify(order).generateInvoice();
        Mockito.verify(invoiceFormatService).format(invoice);
        Mockito.verify(emailService, Mockito.times(2)).send(Mockito.any(EmailMessage.class));
    }

    @Test
    void givenFailedTransaction_whenPlaceOrderFor_thenSendsTransactionEmailOnlyAndReturnsFailedTransaction() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        Mockito.when(buyer.getCartBalance()).thenReturn(cartBalance);
        Mockito.when(buyer.getEmail()).thenReturn(email);
        Mockito.when(paymentService.process(paymentMethod, cartBalance))
                .thenReturn(failedTransaction);
        Mockito.when(failedTransaction.isFailed()).thenReturn(true);
        Mockito.when(failedTransaction.toString()).thenReturn(TRANSACTION_STRING);

        Transaction result = orderApplicationService.placeOrderFor(idul);

        Assertions.assertEquals(failedTransaction, result);
        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(paymentService).process(paymentMethod, cartBalance);
        Mockito.verify(orderFactory, Mockito.never()).create(Mockito.any(), Mockito.any());
        Mockito.verify(invoiceFormatService, Mockito.never()).format(Mockito.any());
        Mockito.verify(emailService, Mockito.times(1)).send(Mockito.any(EmailMessage.class));
    }

    @Test
    void givenSuccessfulTransaction_whenPlaceOrderFor_thenCorrectServicesAreCalled() {
        Mockito.when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        Mockito.when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        Mockito.when(buyer.getCartBalance()).thenReturn(cartBalance);
        Mockito.when(buyer.getEmail()).thenReturn(email);
        Mockito.when(buyer.getCartPasses()).thenReturn(passes);
        Mockito.when(paymentService.process(paymentMethod, cartBalance))
                .thenReturn(successfulTransaction);
        Mockito.when(successfulTransaction.isFailed()).thenReturn(false);
        Mockito.when(successfulTransaction.toString()).thenReturn(TRANSACTION_STRING);
        Mockito.when(orderFactory.create(idul, passes)).thenReturn(order);
        Mockito.when(order.generateInvoice()).thenReturn(invoice);
        Mockito.when(invoiceFormatService.format(invoice)).thenReturn(FORMATTED_INVOICE);

        orderApplicationService.placeOrderFor(idul);

        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(paymentService).process(paymentMethod, cartBalance);
        Mockito.verify(emailService, Mockito.times(2)).send(Mockito.any(EmailMessage.class));
        Mockito.verify(orderFactory).create(idul, passes);
        Mockito.verify(invoiceFormatService).format(invoice);
    }
}
