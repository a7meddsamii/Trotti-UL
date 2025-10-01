package ca.ulaval.glo4003.trotti.application.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.communication.EmailMessage;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.domain.order.*;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.domain.payment.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.domain.payment.services.PaymentService;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
        buyerRepository = mock(BuyerRepository.class);
        orderFactory = mock(OrderFactory.class);
        paymentService = mock(PaymentService.class);
        emailService = mock(EmailService.class);
        invoiceFormatService = mock(InvoiceFormatService.class);
        buyer = mock(Buyer.class);
        paymentMethod = mock(PaymentMethod.class);
        successfulTransaction = mock(Transaction.class);
        failedTransaction = mock(Transaction.class);
        order = mock(Order.class);
        invoice = mock(Invoice.class);
        idul = mock(Idul.class);
        email = mock(Email.class);
        cartBalance = mock(Money.class);
        pass1 = mock(Pass.class);
        pass2 = mock(Pass.class);

        orderApplicationService = new OrderApplicationService(buyerRepository, orderFactory,
                paymentService, emailService, invoiceFormatService);
        passes = Arrays.asList(pass1, pass2);
    }

    @Test
    void givenBuyer_whenPlaceOrderForSuccessfulTransaction_thenProcessesPaymentAndSendsEmails() {
        when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        when(buyer.getCartBalance()).thenReturn(cartBalance);
        when(buyer.getEmail()).thenReturn(email);
        when(buyer.getCartPasses()).thenReturn(passes);
        when(paymentService.process(paymentMethod, cartBalance)).thenReturn(successfulTransaction);
        when(successfulTransaction.isFailed()).thenReturn(false);
        when(successfulTransaction.toString()).thenReturn(TRANSACTION_STRING);
        when(orderFactory.create(idul, passes)).thenReturn(order);
        when(order.generateInvoice()).thenReturn(invoice);
        when(invoiceFormatService.format(invoice)).thenReturn(FORMATTED_INVOICE);

        Transaction result = orderApplicationService.placeOrderFor(idul);

        assertEquals(successfulTransaction, result);
        verify(buyerRepository).findByIdul(idul);
        verify(paymentService).process(paymentMethod, cartBalance);
        verify(orderFactory).create(idul, passes);
        verify(order).generateInvoice();
        verify(invoiceFormatService).format(invoice);
        verify(emailService, times(2)).send(any(EmailMessage.class));
    }

    @Test
    void givenValidIdul_whenPlaceOrderForFailedTransaction_thenSendsTransactionEmailOnlyAndReturnsFailedTransaction() {
        when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        when(buyer.getCartBalance()).thenReturn(cartBalance);
        when(buyer.getEmail()).thenReturn(email);
        when(paymentService.process(paymentMethod, cartBalance)).thenReturn(failedTransaction);
        when(failedTransaction.isFailed()).thenReturn(true);
        when(failedTransaction.toString()).thenReturn(TRANSACTION_STRING);

        Transaction result = orderApplicationService.placeOrderFor(idul);

        assertEquals(failedTransaction, result);
        verify(buyerRepository).findByIdul(idul);
        verify(paymentService).process(paymentMethod, cartBalance);
        verify(orderFactory, never()).create(any(), any());
        verify(invoiceFormatService, never()).format(any());
        verify(emailService, times(1)).send(any(EmailMessage.class));
    }

    @Test
    void givenValidIdul_whenPlaceOrderForSuccessfulTransaction_thenSendsCorrectTransactionEmail() {
        when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        when(buyer.getCartBalance()).thenReturn(cartBalance);
        when(buyer.getEmail()).thenReturn(email);
        when(buyer.getCartPasses()).thenReturn(passes);
        when(paymentService.process(paymentMethod, cartBalance)).thenReturn(successfulTransaction);
        when(successfulTransaction.isFailed()).thenReturn(false);
        when(successfulTransaction.toString()).thenReturn(TRANSACTION_STRING);
        when(orderFactory.create(idul, passes)).thenReturn(order);
        when(order.generateInvoice()).thenReturn(invoice);
        when(invoiceFormatService.format(invoice)).thenReturn(FORMATTED_INVOICE);

        ArgumentCaptor<EmailMessage> emailCaptor = ArgumentCaptor.forClass(EmailMessage.class);

        orderApplicationService.placeOrderFor(idul);

        verify(emailService, times(2)).send(emailCaptor.capture());
        List<EmailMessage> sentEmails = emailCaptor.getAllValues();
        EmailMessage transactionEmail = sentEmails.get(0);
        assertEquals(email, transactionEmail.getRecipient());
        assertEquals(TRANSACTION_STRING, transactionEmail.getBody());

        EmailMessage invoiceEmail = sentEmails.get(1);
        assertEquals(email, invoiceEmail.getRecipient());
        assertEquals("Your Invoice", invoiceEmail.getSubject());
        assertEquals(FORMATTED_INVOICE, invoiceEmail.getBody());
    }

    @Test
    void givenValidIdul_whenPlaceOrderForFailedTransaction_thenSendsCorrectTransactionEmailOnly() {
        when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        when(buyer.getCartBalance()).thenReturn(cartBalance);
        when(buyer.getEmail()).thenReturn(email);
        when(paymentService.process(paymentMethod, cartBalance)).thenReturn(failedTransaction);
        when(failedTransaction.isFailed()).thenReturn(true);
        when(failedTransaction.toString()).thenReturn(TRANSACTION_STRING);

        ArgumentCaptor<EmailMessage> emailCaptor = ArgumentCaptor.forClass(EmailMessage.class);

        orderApplicationService.placeOrderFor(idul);

        verify(emailService).send(emailCaptor.capture());
        EmailMessage sentEmail = emailCaptor.getValue();
        assertEquals(email, sentEmail.getRecipient());
        assertEquals(TRANSACTION_STRING, sentEmail.getBody());
    }

    @Test
    void givenValidIdul_whenPlaceOrderForSuccessfulTransaction_thenCallsServicesInCorrectOrder() {
        when(buyerRepository.findByIdul(idul)).thenReturn(buyer);
        when(buyer.getPaymentMethod()).thenReturn(Optional.of(paymentMethod));
        when(buyer.getCartBalance()).thenReturn(cartBalance);
        when(buyer.getEmail()).thenReturn(email);
        when(buyer.getCartPasses()).thenReturn(passes);
        when(paymentService.process(paymentMethod, cartBalance)).thenReturn(successfulTransaction);
        when(successfulTransaction.isFailed()).thenReturn(false);
        when(successfulTransaction.toString()).thenReturn(TRANSACTION_STRING);
        when(orderFactory.create(idul, passes)).thenReturn(order);
        when(order.generateInvoice()).thenReturn(invoice);
        when(invoiceFormatService.format(invoice)).thenReturn(FORMATTED_INVOICE);

        orderApplicationService.placeOrderFor(idul);

        Mockito.verify(buyerRepository).findByIdul(idul);
        Mockito.verify(paymentService).process(paymentMethod, cartBalance);
        Mockito.verify(emailService, Mockito.times(2)).send(any(EmailMessage.class));
        Mockito.verify(orderFactory).create(idul, passes);
        Mockito.verify(invoiceFormatService).format(invoice);
    }
}
