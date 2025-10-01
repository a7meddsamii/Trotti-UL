package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentMethodException;
import ca.ulaval.glo4003.trotti.domain.payment.utilities.SecuredString;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;

public class CreditCard extends PaymentMethod {

    private static final String CVV_3_OR_4_DIGITS_REGEX = "^\\d{3,4}$";

    private final SecuredString cardNumber;
    private final String cardHolderName;
    private final YearMonth expiryDate;
    private final String cvv;

    private CreditCard(
            SecuredString cardNumber,
            String cardHolderName,
            YearMonth expiryDate,
            String cvv) {
        validate(cardNumber, cardHolderName, cvv);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public static CreditCard from(SecuredString cardNumber, String cardHolderName,
            YearMonth expiryDate, String cvv) {
        return new CreditCard(cardNumber, cardHolderName, expiryDate, cvv);
    }

    public String getCardNumber() {
        return cardNumber.getMasked();
    }

    @Override
    public void pay(Money amount) {
        System.out.println("Paid " + amount + " with credit card ending in " + getCardNumber());
    }

    @Override
    public boolean isExpired() {
        return expiryDate.isBefore(YearMonth.now());
    }

    private void validate(SecuredString cardNumber, String cardHolderName, String cvv) {
        if (cardNumber == null) {
            throw new InvalidPaymentMethodException("Card number cannot be empty");
        }

        if (StringUtils.isBlank(cardHolderName)) {
            throw new InvalidPaymentMethodException("Cardholder name cannot be empty");
        }

        if (!isValidCvv(cvv)) {
            throw new InvalidPaymentMethodException("CVV must be 3 or 4 digits");
        }
    }

    private boolean isValidCvv(String cvv) {
        return cvv != null && cvv.matches(CVV_3_OR_4_DIGITS_REGEX);
    }
}
