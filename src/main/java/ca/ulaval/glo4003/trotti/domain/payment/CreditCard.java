package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.payment.exceptions.InvalidPaymentMethodException;
import ca.ulaval.glo4003.trotti.domain.payment.utilities.SecuredString;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;

public class CreditCard implements PaymentMethod {

    private final SecuredString cardNumber;
    private final String cardHolderName;
    private final YearMonth expiryDate;

    private CreditCard(SecuredString cardNumber, String cardHolderName, YearMonth expiryDate) {
        validate(cardNumber, cardHolderName);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
    }

    public static CreditCard from(SecuredString cardNumber, String cardHolderName,
            YearMonth expiryDate) {
        return new CreditCard(cardNumber, cardHolderName, expiryDate);
    }

    public String getCardNumber() {
        return cardNumber.getMasked();
    }

    public SecuredString getSecuredString() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }

    @Override
    public void pay(Money amount) {
        System.out.println("Paid " + amount + " with credit card ending in " + getCardNumber());
    }

    @Override
    public boolean isExpired() {
        return expiryDate.isBefore(YearMonth.now());
    }

    private void validate(SecuredString cardNumber, String cardHolderName) {
        if (cardNumber == null) {
            throw new InvalidPaymentMethodException("Card number cannot be empty");
        }

        if (StringUtils.isBlank(cardHolderName)) {
            throw new InvalidPaymentMethodException("Cardholder name cannot be empty");
        }
    }
}
