package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.PaymentMethod;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentDeclinedException;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
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
    void givenNullPaymentMethod_whenProcess_thenThrowException() {
        Executable executable = () -> paymentService.process(null, amountToPay);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenNullAmountToPay_whenProcess_thenThrowException() {
        Executable executable = () -> paymentService.process(paymentMethod, null);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenNegativeAmountToPay_whenProcess_thenThrowException() {
        Mockito.when(amountToPay.isNegative()).thenReturn(true);

        Executable executable = () -> paymentService.process(paymentMethod, amountToPay);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenExpiredPaymentMethod_whenProcess_thenThrowException() {
        Mockito.when(paymentMethod.isExpired()).thenReturn(true);

        Executable executable = () -> paymentService.process(paymentMethod, amountToPay);

        Assertions.assertThrows(PaymentDeclinedException.class, executable);
    }

    @Test
    void givenValidPaymentMethod_whenProcess_thenPaymentMethodPays() {
        Mockito.when(paymentMethod.isExpired()).thenReturn(false);
        Mockito.when(amountToPay.isNegative()).thenReturn(false);

        paymentService.process(paymentMethod, amountToPay);

        Mockito.verify(paymentMethod).pay(amountToPay);
    }
}
