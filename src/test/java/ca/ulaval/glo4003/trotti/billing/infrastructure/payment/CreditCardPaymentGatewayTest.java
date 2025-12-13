package ca.ulaval.glo4003.trotti.billing.infrastructure.payment;


import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions.MissingPaymentMethodException;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.PaymentReceipt;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentIntent;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethodType;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.billing.infrastructure.payment.CreditCardPaymentGateway;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.Clock;
import java.time.Instant;
import java.time.YearMonth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditCardPaymentGatewayTest {

    private static final Idul BUYER_1_IDUL = Idul.from("buyer001");
    private static final Idul BUYER_2_IDUL = Idul.from("buyer002");
    private static final Idul BUYER_3_IDUL = Idul.from("buyer003");

    @Mock
    private CreditCard creditCard;

    @Mock
    private Money amount;

    @Mock
    private OrderId orderId;

    private Clock clock = Clock.systemDefaultZone();
    private CreditCardPaymentGateway creditCardPaymentGateway;

    @BeforeEach
    void setup() {

        creditCardPaymentGateway = new CreditCardPaymentGateway(clock);
    }

    @Test
    void givenPaymentIntentWithCreditCard_whenPay_thenPaymentSucceeds() {
        Mockito.when(creditCard.isEmpty()).thenReturn(false);
        Mockito.when(creditCard.isType(PaymentMethodType.CREDIT_CARD)).thenReturn(true);
        Mockito.when(creditCard.getExpiryDate()).thenReturn(YearMonth.now(clock));
        PaymentIntent paymentIntent = PaymentIntent.of(BUYER_1_IDUL, orderId, amount, creditCard, false);

        PaymentReceipt result = creditCardPaymentGateway.pay(paymentIntent);

        Assertions.assertTrue(result.isSuccess());
    }

    @Test
    void givenNewBuyerWithoutCreditCard_whenPay_thenThrowException() {
        Mockito.when(creditCard.isEmpty()).thenReturn(true);
        PaymentIntent paymentIntent = PaymentIntent.of(BUYER_2_IDUL, orderId, amount, creditCard, false);

        Executable executable = () -> creditCardPaymentGateway.pay(paymentIntent);

        Assertions.assertThrows(MissingPaymentMethodException.class, executable);
    }

    @Test
    void givenBuyerWithStoredCard_whenPayWithoutProvidingCard_thenPaymentSucceeds() {
        Mockito.when(creditCard.isEmpty()).thenReturn(false);
        Mockito.when(creditCard.isType(PaymentMethodType.CREDIT_CARD)).thenReturn(true);
        Mockito.when(creditCard.getExpiryDate()).thenReturn(YearMonth.now(clock));
        PaymentIntent firstPayment = PaymentIntent.of(BUYER_3_IDUL, orderId, amount, creditCard, false);
        creditCardPaymentGateway.pay(firstPayment);

        Mockito.when(creditCard.isEmpty()).thenReturn(true);
        PaymentIntent secondPayment = PaymentIntent.of(BUYER_3_IDUL, orderId, amount, creditCard, true);

        PaymentReceipt result = creditCardPaymentGateway.pay(secondPayment);

        Assertions.assertTrue(result.isSuccess());
    }

}
