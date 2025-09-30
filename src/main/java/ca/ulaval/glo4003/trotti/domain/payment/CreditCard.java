package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.CreditCardValidator;

public class CreditCard extends PaymentMethod {

    private static final String CVV_3_OR_4_DIGITS_REGEX = "^\\d{3,4}$";
    private static final int DIGITS_TO_DISPLAY = 4;

    private static final CreditCardValidator VALIDATOR = new CreditCardValidator();

    private final String cardNumber;
    private final String cardHolderName;
    private final YearMonth expiryDate;
    private final String cvv;

    public CreditCard(String cardNumber, String cardHolderName, YearMonth expiryDate, String cvv) {
        validate(cardNumber, cardHolderName, cvv);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber.substring(cardNumber.length() - DIGITS_TO_DISPLAY);
    }

    @Override
    public void pay(Money amount) {
        System.out.println("Paid " + amount + " with credit card ending in " + getCardNumber());
    }

    @Override
    public boolean isExpired() {
        return expiryDate.isBefore(YearMonth.now());
    }

    private void validate(String cardNumber, String cardHolderName, String cvv) {
        if (StringUtils.isBlank(cardHolderName)) {
            throw new InvalidParameterException("Cardholder name cannot be empty");
        }

        if (!VALIDATOR.isValid(cardNumber) || !isValidCvv(cvv)) {
            throw new InvalidParameterException("Invalid Credit Card");
        }
    }

    private boolean isValidCvv(String cvv) {
        return cvv != null && cvv.matches(CVV_3_OR_4_DIGITS_REGEX);
    }

    @Override
    public String generateInvoice() {
        return "\nPaid with Credit Card ending by " + getCardNumber() + "and held by "
                + cardHolderName + "\n";
    }
}
