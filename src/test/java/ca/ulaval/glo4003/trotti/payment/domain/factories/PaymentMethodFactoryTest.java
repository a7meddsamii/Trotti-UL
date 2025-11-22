package ca.ulaval.glo4003.trotti.payment.domain.factories;

import ca.ulaval.glo4003.trotti.billing.domain.payment.factories.PaymentMethodFactory;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.billing.domain.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.CreditCard;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class PaymentMethodFactoryTest {

    private static final String ENCODED_CARD_NUMBER = "encodedCardNumber";
    private static final String VALID_CARD_NUMBER = "4111111111111111";
    private static final String LAST_FOUR_DIGITS = "1111";
    private static final String VALID_CARD_HOLDER_NAME = "John Doe";
    private static final YearMonth VALID_EXPIRATION_DATE = YearMonth.now().plusMonths(1);
    private static final String VALID_CVV = "123";

    private DataCodec dataCodec;
    private PaymentMethodFactory paymentMethodFactory;

    @BeforeEach
    void setup() {
        dataCodec = Mockito.mock(DataCodec.class);
        paymentMethodFactory = new PaymentMethodFactory(dataCodec);
    }

    @Test
    void givenPaymentInfoParams_whenCreateCreditCard_thenReturnsCreditCardWithEncodedData() {
        Mockito.when(dataCodec.encode(VALID_CARD_NUMBER)).thenReturn(ENCODED_CARD_NUMBER);

        CreditCard creditCard = paymentMethodFactory.createCreditCard(VALID_CARD_NUMBER,
                VALID_CARD_HOLDER_NAME, VALID_EXPIRATION_DATE, VALID_CVV);

        Assertions.assertEquals(ENCODED_CARD_NUMBER, creditCard.getSecuredString().getEncoded());
        Mockito.verify(dataCodec).encode(VALID_CARD_NUMBER);
    }

    @Test
    void givenPaymentInfoParams_whenCreateCreditCard_thenReturnsCreditCardWithCorrectData() {
        Mockito.when(dataCodec.encode(VALID_CARD_NUMBER)).thenReturn(ENCODED_CARD_NUMBER);

        CreditCard creditCard = paymentMethodFactory.createCreditCard(VALID_CARD_NUMBER,
                VALID_CARD_HOLDER_NAME, VALID_EXPIRATION_DATE, VALID_CVV);

        Assertions.assertEquals(VALID_CARD_HOLDER_NAME, creditCard.getCardHolderName());
        Assertions.assertEquals(VALID_EXPIRATION_DATE, creditCard.getExpiryDate());
        Assertions.assertEquals(LAST_FOUR_DIGITS, creditCard.getCardNumber());
    }

    @Test
    void givenInvalidCardNumber_whenCreateCreditCard_thenException() {
        String invalidCardNumber = "1234";

        Executable createCreditCardAction =
                () -> paymentMethodFactory.createCreditCard(invalidCardNumber,
                        VALID_CARD_HOLDER_NAME, VALID_EXPIRATION_DATE, VALID_CVV);

        Assertions.assertThrows(InvalidParameterException.class, createCreditCardAction);
    }

    @Test
    void givenInvalidCardHolderName_whenCreateCreditCard_thenThrowException() {
        String invalidCardHolderName = StringUtils.SPACE;

        Executable createCreditCardAction =
                () -> paymentMethodFactory.createCreditCard(VALID_CARD_NUMBER,
                        invalidCardHolderName, VALID_EXPIRATION_DATE, VALID_CVV);

        Assertions.assertThrows(InvalidParameterException.class, createCreditCardAction);
    }

    @Test
    void givenInvalidExpirationDate_whenCreateCreditCard_thenThrowException() {
        YearMonth invalidExpirationDate = YearMonth.now().minusMonths(1);

        Executable createCreditCardAction =
                () -> paymentMethodFactory.createCreditCard(VALID_CARD_NUMBER,
                        VALID_CARD_HOLDER_NAME, invalidExpirationDate, VALID_CVV);

        Assertions.assertThrows(InvalidParameterException.class, createCreditCardAction);
    }

    @Test
    void givenNoCardNumber_whenCreateCreditCard_thenThrowException() {
        Executable createCreditCardAction = () -> paymentMethodFactory.createCreditCard(null,
                VALID_CARD_HOLDER_NAME, VALID_EXPIRATION_DATE, VALID_CVV);

        Assertions.assertThrows(InvalidParameterException.class, createCreditCardAction);
    }

    @Test
    void givenNoCardHolderName_whenCreateCreditCard_thenThrowException() {
        Executable createCreditCardAction = () -> paymentMethodFactory
                .createCreditCard(VALID_CARD_NUMBER, null, VALID_EXPIRATION_DATE, VALID_CVV);

        Assertions.assertThrows(InvalidParameterException.class, createCreditCardAction);
    }

    @Test
    void givenNoExpirationDate_whenCreateCreditCard_thenThrowException() {
        Executable createCreditCardAction = () -> paymentMethodFactory
                .createCreditCard(VALID_CARD_NUMBER, VALID_CARD_HOLDER_NAME, null, VALID_CVV);

        Assertions.assertThrows(InvalidParameterException.class, createCreditCardAction);
    }

}
