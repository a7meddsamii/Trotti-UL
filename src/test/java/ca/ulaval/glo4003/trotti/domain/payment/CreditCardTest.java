package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentDeclinedException;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.fixtures.CreditCardFixture;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class CreditCardTest {

    private static final String INVALID_CARD_NUMBER = "111111111111111";
    private static final YearMonth EXPIRED_DATE = YearMonth.now().minusMonths(1);
    private static final String INVALID_CVV = "12";

    private Money money = Mockito.mock(Money.class);

    @Test
    void givenInvalidCardNumber_whenCreatingCreditCard_thenThrowsException() {
        Executable creatingInvalidCreditCard =
                () -> new CreditCardFixture().withCardNumber(INVALID_CARD_NUMBER).build();

        Assertions.assertThrows(InvalidParameterException.class, creatingInvalidCreditCard);
    }

    @Test
    void givenInvalidCvv_whenCreatingCreditCard_thenThrowsException() {
        Executable creatingInvalidCreditCard =
                () -> new CreditCardFixture().withCvv(INVALID_CVV).build();

        Assertions.assertThrows(InvalidParameterException.class, creatingInvalidCreditCard);
    }

    @Test
    void givenEmptyCardHolder_whenCreatingCreditCard_thenThrowsException() {
        Executable creatingInvalidCreditCard =
                () -> new CreditCardFixture().withCardHolderName(StringUtils.EMPTY).build();

        Assertions.assertThrows(InvalidParameterException.class, creatingInvalidCreditCard);
    }

    @Test
    void givenValidCreditCard_whenPay_thenDoesNotThrowException() {
        CreditCard creditCard = new CreditCardFixture().build();

        Executable payWithValidCreditCard = () -> creditCard.pay(money);

        Assertions.assertDoesNotThrow(payWithValidCreditCard);
    }

}
