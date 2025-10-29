package ca.ulaval.glo4003.trotti.domain.commons.payment.services;

import ca.ulaval.glo4003.trotti.payment.domain.services.PaymentService;
import ca.ulaval.glo4003.trotti.payment.domain.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import ca.ulaval.glo4003.trotti.payment.domain.values.transaction.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PaymentServiceTest {

    private static final String A_VALID_CVV = "123";
    private static final String AN_INVALID_CVV = "1";

    private PaymentService paymentService;
    private Money amountToPay;
    private CreditCard paymentMethod;

    @BeforeEach
    void setup() {
        amountToPay = Mockito.mock(Money.class);
        paymentMethod = Mockito.mock(CreditCard.class);

        paymentService = new PaymentService();
    }

    @Test
    void givenNullAmountToPay_whenProcess_thenReturnFailedTransaction() {
        Transaction transaction = paymentService.process(paymentMethod, null, A_VALID_CVV);

        Assertions.assertFalse(transaction.isSuccessful());
    }

    @Test
    void givenNegativeAmountToPay_whenProcess_thenReturnFailedTransaction() {
        Mockito.when(amountToPay.isNegative()).thenReturn(true);

        Transaction transaction = paymentService.process(paymentMethod, amountToPay, A_VALID_CVV);

        Assertions.assertFalse(transaction.isSuccessful());
    }

    @Test
    void givenExpiredPaymentMethod_whenProcess_thenReturnFailedTransaction() {
        Mockito.when(paymentMethod.isExpired()).thenReturn(true);

        Transaction transaction = paymentService.process(paymentMethod, amountToPay, A_VALID_CVV);

        Assertions.assertFalse(transaction.isSuccessful());
    }

    @Test
    void givenInvalidCvv_whenProcess_thenReturnFailedTransaction() {
        Transaction transaction =
                paymentService.process(paymentMethod, amountToPay, AN_INVALID_CVV);

        Assertions.assertFalse(transaction.isSuccessful());
    }

    @Test
    void givenValidPaymentMethod_whenProcess_thenReturnSuccessfulTransaction() {
        Mockito.when(paymentMethod.isExpired()).thenReturn(false);
        Mockito.when(amountToPay.isNegative()).thenReturn(false);

        Transaction transaction = paymentService.process(paymentMethod, amountToPay, A_VALID_CVV);

        Assertions.assertTrue(transaction.isSuccessful());
        Mockito.verify(paymentMethod).pay(amountToPay);
    }
}
