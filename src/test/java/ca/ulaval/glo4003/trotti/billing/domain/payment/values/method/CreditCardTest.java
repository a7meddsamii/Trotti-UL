package ca.ulaval.glo4003.trotti.billing.domain.payment.values.method;

import ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions.MissingPaymentMethodException;
import ca.ulaval.glo4003.trotti.billing.domain.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.SecuredString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.YearMonth;

class CreditCardTest {

    private static final String VALID_CARD_NUMBER = "4111111111111111";
    private static final String VALID_CARD_HOLDER = "John Doe";
    private static final String INVALID_CARD_HOLDER = "";
    private static final String EXPECTED_LAST_FOUR_DIGITS = "1111";

    private final DataCodec encoder = Mockito.mock(DataCodec.class);

    @Test
    void givenEmptyCardHolder_whenCreatingCreditCard_thenThrowsException() {
        SecuredString validSecured = securedStringFromRaw(VALID_CARD_NUMBER);
        Executable creatingInvalidCreditCard = () -> CreditCard.from(validSecured,
																	 INVALID_CARD_HOLDER, YearMonth.now().plusYears(1));

        Assertions.assertThrows(MissingPaymentMethodException.class, creatingInvalidCreditCard);
    }

    @Test
    void givenNullCardNumber_whenCreatingCreditCard_thenThrowsException() {
        Executable creatingInvalidCreditCard =
                () -> CreditCard.from(null, VALID_CARD_HOLDER, YearMonth.now().plusYears(1));

        Assertions.assertThrows(MissingPaymentMethodException.class, creatingInvalidCreditCard);
    }

    @Test
    void givenNullCardHolderName_whenCreatingCreditCard_thenThrowsException() {
        SecuredString validSecured = securedStringFromRaw(VALID_CARD_NUMBER);
        Executable creatingInvalidCreditCard =
                () -> CreditCard.from(validSecured, null, YearMonth.now().plusYears(1));

        Assertions.assertThrows(MissingPaymentMethodException.class, creatingInvalidCreditCard);
    }
    @Test
    void givenCreditCard_whenGetCardNumber_thenReturnsLastFourDigits() {
        SecuredString validSecured = securedStringFromRaw(VALID_CARD_NUMBER);
        CreditCard creditCard =
                CreditCard.from(validSecured, VALID_CARD_HOLDER, YearMonth.now().plusYears(1));

        String lastFourDigits = creditCard.getCardNumber();

        Assertions.assertEquals(EXPECTED_LAST_FOUR_DIGITS, lastFourDigits);
    }

    @Test
    void givenExpiredCreditCard_whenIsExpired_thenReturnsTrue() {
        SecuredString validSecured = securedStringFromRaw(VALID_CARD_NUMBER);
        YearMonth expiredDate = YearMonth.now().minusMonths(1);
        CreditCard creditCard = CreditCard.from(validSecured, VALID_CARD_HOLDER, expiredDate);

        Assertions.assertTrue(creditCard.isExpired());
    }

    @Test
    void givenNonExpiredCreditCard_whenIsExpired_thenReturnsFalse() {
        SecuredString validSecured = securedStringFromRaw(VALID_CARD_NUMBER);
        YearMonth futureDate = YearMonth.now().plusMonths(1);
        CreditCard creditCard = CreditCard.from(validSecured, VALID_CARD_HOLDER, futureDate);

        Assertions.assertFalse(creditCard.isExpired());
    }

    private SecuredString securedStringFromRaw(String raw) {
        Mockito.when(encoder.encode(raw)).thenReturn("encoded_" + raw);
        return SecuredString.fromPlain(raw, encoder);
    }
}
