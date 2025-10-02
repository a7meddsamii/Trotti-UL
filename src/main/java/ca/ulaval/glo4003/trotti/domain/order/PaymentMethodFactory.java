package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.domain.payment.services.DataEncoder;
import ca.ulaval.glo4003.trotti.domain.payment.utilities.SecuredString;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.CreditCardValidator;

public class PaymentMethodFactory {

    private static final String CVV_REGEX = "^\\d{3,4}$";

    private final CreditCardValidator creditCardValidator = new CreditCardValidator();
    private final DataEncoder dataEncoder;

    public PaymentMethodFactory(DataEncoder dataEncoder) {
        this.dataEncoder = dataEncoder;
    }

    public CreditCard createCreditCard(String cardNumber, String cardHolderName,
            YearMonth expirationDate, String cvv) {
        validateNotBlank(cardNumber, cardHolderName, expirationDate, cvv);
        validateCardNumber(cardNumber);
        validateExpirationDate(expirationDate);
        validateCvv(cvv);

        return CreditCard.from(SecuredString.fromPlain(cardNumber, dataEncoder), cardHolderName,
                expirationDate);
    }

    private void validateNotBlank(String cardNumber, String cardHolderName, YearMonth expirationDate, String cvv) {
        if (StringUtils.isBlank(cardNumber) || StringUtils.isBlank(cardHolderName)
                || expirationDate == null || StringUtils.isBlank(cvv)) {
            throw new InvalidParameterException(
                    "All fields must be provided: card number, card holder name, expiration date, cvv");
        }
    }

    private void validateCvv(String cvv) {
        if (!cvv.matches(CVV_REGEX)) {
            throw new InvalidParameterException("Invalid CVV");
        }
    }

    private void validateExpirationDate(YearMonth expirationDate) {
        YearMonth currentYearMonth = YearMonth.now();
        if (expirationDate.isBefore(currentYearMonth)) {
            throw new InvalidParameterException("Expired credit card");
        }
    }

    private void validateCardNumber(String cardNumber) {
        if (creditCardValidator.isValid(cardNumber)) {
            throw new InvalidParameterException("Invalid card number");
        }
    }
}
