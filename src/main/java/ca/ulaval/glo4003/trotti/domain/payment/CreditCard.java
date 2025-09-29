package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentDeclinedException;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.CreditCardValidator;

public class CreditCard extends PaymentMethod {

    private static final CreditCardValidator VALIDATOR = new CreditCardValidator(
            CreditCardValidator.VISA + CreditCardValidator.MASTERCARD + CreditCardValidator.AMEX
                    + CreditCardValidator.DINERS + CreditCardValidator.DISCOVER);

    private final String cardNumber;
    private final String cardHolderName;
    private final YearMonth expiryDate;
    private final String cvv;

    public CreditCard(String cardNumber, String cardHolderName, YearMonth expiryDate, String cvv) {
        validate(cardNumber, cardHolderName, expiryDate, cvv);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void pay(Money amount) throws PaymentDeclinedException {
        if (isExpired()) {
            throw new PaymentDeclinedException("Credit card expired");
        }
    }

    private void validate(String cardNumber, String cardHolderName, YearMonth expiryDate,
            String cvv) {
        if (StringUtils.isBlank(cardHolderName)) {
            throw new InvalidParameterException("Cardholder name cannot be empty");
        }

        if (!VALIDATOR.isValid(cardNumber) || !isValidExpiryDate(expiryDate) || !isValidCvv(cvv)) {
            throw new InvalidParameterException("Invalid Credit Card");
        }
    }

    private boolean isExpired() {
        return expiryDate.isBefore(YearMonth.now());
    }

    private boolean isValidExpiryDate(YearMonth expiryDate) {
        return expiryDate != null && !expiryDate.isBefore(YearMonth.now());
    }

    private boolean isValidCvv(String cvv) {
        return cvv != null && cvv.matches("^\\d{3,4}$");
    }
}
