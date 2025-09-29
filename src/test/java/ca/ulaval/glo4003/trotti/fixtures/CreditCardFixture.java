package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import java.time.YearMonth;

public class CreditCardFixture {

    public static final String A_VALID_CREDIT_CARD_NUMBER = "4111111111111111";
    public static final String A_VALID_CARD_HOLDER_NAME = "John Doe";
    public static final YearMonth A_VALID_EXPIRATION_DATE = YearMonth.now().plusYears(2);
    public static final String A_VALID_CVV = "123";

    private String cardNumber = A_VALID_CREDIT_CARD_NUMBER;
    private String cardHolderName = A_VALID_CARD_HOLDER_NAME;
    private YearMonth expiryDate = A_VALID_EXPIRATION_DATE;
    private String cvv = A_VALID_CVV;

    public CreditCardFixture withCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public CreditCardFixture withCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        return this;
    }

    public CreditCardFixture withExpiryDate(YearMonth expirationDate) {
        this.expiryDate = expirationDate;
        return this;
    }

    public CreditCardFixture withCvv(String securityCode) {
        this.cvv = securityCode;
        return this;
    }

    public CreditCard build() {
        return new CreditCard(cardNumber, cardHolderName, expiryDate, cvv);
    }
}
