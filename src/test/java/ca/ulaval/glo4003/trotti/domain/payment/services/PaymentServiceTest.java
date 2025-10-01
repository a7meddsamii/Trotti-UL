package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PaymentServiceTest {

    private PaymentService paymentService;
    private Money amountToPay;
    private PaymentMethod paymentMethod;

    @BeforeEach
    void setup() {
        amountToPay = Mockito.mock(Money.class);
        paymentMethod = Mockito.mock(PaymentMethod.class);

        paymentService = new PaymentService();
    }

    @Test
    void givenNullAmountToPay_whenProcess_thenReturnFailedTransaction() {
        Transaction transaction = paymentService.process(paymentMethod, null);

        Assertions.assertTrue(transaction.isFailed());
    }

    @Test
    void givenNegativeAmountToPay_whenProcess_thenReturnFailedTransaction() {
        Mockito.when(amountToPay.isNegative()).thenReturn(true);

        Transaction transaction = paymentService.process(paymentMethod, amountToPay);

        Assertions.assertTrue(transaction.isFailed());
    }

    @Test
    void givenExpiredPaymentMethod_whenProcess_thenReturnFailedTransaction() {
        Mockito.when(paymentMethod.isExpired()).thenReturn(true);

        Transaction transaction = paymentService.process(paymentMethod, amountToPay);

        Assertions.assertTrue(transaction.isFailed());
    }

    @Test
    void givenValidPaymentMethod_whenProcess_thenReturnSuccessfulTransaction() {
        Mockito.when(paymentMethod.isExpired()).thenReturn(false);
        Mockito.when(amountToPay.isNegative()).thenReturn(false);

        Transaction transaction = paymentService.process(paymentMethod, amountToPay);

        Assertions.assertFalse(transaction.isFailed());
        Mockito.verify(paymentMethod).pay(amountToPay);
    }
}
