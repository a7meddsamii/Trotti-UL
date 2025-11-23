package ca.ulaval.glo4003.trotti.billing.infrastructure.payment.security;

import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions.MissingPaymentMethodException;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentReceipt;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentIntent;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethodType;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.billing.infrastructure.payment.CreditCardPaymentGateway;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditCardPaymentGatewayTest {

    @Mock
    private CreditCard creditCard;

    @Mock
    private Money amount;

    @Mock
    private OrderId orderId;

    private CreditCardPaymentGateway creditCardPaymentGateway;

    @BeforeEach
    void setup() {
        creditCardPaymentGateway = new CreditCardPaymentGateway();
    }

    @Test
    void givenPaymentIntentWithCreditCard_whenPay_thenPaymentSucceeds() {
        Idul buyerId = Idul.from("BUYER_001");
        when(creditCard.isEmpty()).thenReturn(false);
        when(creditCard.isType(PaymentMethodType.CREDIT_CARD)).thenReturn(true);
        PaymentIntent paymentIntent = PaymentIntent.of(buyerId, orderId, amount, creditCard, false);

        PaymentReceipt result = creditCardPaymentGateway.pay(paymentIntent);

        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    void givenNewBuyerWithoutCreditCard_whenPay_thenThrowException() {
        Idul buyerId = Idul.from("BUYER_002");
        when(creditCard.isEmpty()).thenReturn(true);
        PaymentIntent paymentIntent = PaymentIntent.of(buyerId, orderId, amount, creditCard, false);

        Executable executable = () -> creditCardPaymentGateway.pay(paymentIntent);

        Assertions.assertThrows(MissingPaymentMethodException.class, executable);
    }

    @Test
    void givenBuyerWithStoredCard_whenPayWithoutProvidingCard_thenPaymentSucceeds() {
        Idul buyerId = Idul.from("BUYER_003");
        when(creditCard.isEmpty()).thenReturn(false);
        when(creditCard.isType(PaymentMethodType.CREDIT_CARD)).thenReturn(true);
        PaymentIntent firstPayment = PaymentIntent.of(buyerId, orderId, amount, creditCard, false);
        creditCardPaymentGateway.pay(firstPayment);

        when(creditCard.isEmpty()).thenReturn(true);
        PaymentIntent secondPayment = PaymentIntent.of(buyerId, orderId, amount, creditCard, true);

        PaymentReceipt result = creditCardPaymentGateway.pay(secondPayment);

        Assertions.assertTrue(result.isSuccess());
    }

}
