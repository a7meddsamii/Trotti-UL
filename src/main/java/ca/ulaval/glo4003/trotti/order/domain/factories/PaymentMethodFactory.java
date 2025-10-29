package ca.ulaval.glo4003.trotti.order.domain.factories;

import ca.ulaval.glo4003.trotti.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.payment.domain.security.DataCodec;
import ca.ulaval.glo4003.trotti.payment.domain.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.payment.domain.values.method.SecuredString;
import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.CreditCardValidator;

public class PaymentMethodFactory {

    private final CreditCardValidator creditCardValidator = new CreditCardValidator();
    private final DataCodec dataCodec;

    public PaymentMethodFactory(DataCodec dataCodec) {
        this.dataCodec = dataCodec;
    }

    public CreditCard createCreditCard(String cardNumber, String cardHolderName,
            YearMonth expirationDate, String cvv) {
        validateNotBlank(cardNumber, cardHolderName, expirationDate, cvv);
        validateCardNumber(cardNumber);
        validateExpirationDate(expirationDate);

        return CreditCard.from(SecuredString.fromPlain(cardNumber, dataCodec), cardHolderName,
                expirationDate);
    }

    private void validateNotBlank(String cardNumber, String cardHolderName,
            YearMonth expirationDate, String cvv) {
        if (StringUtils.isBlank(cardNumber) || StringUtils.isBlank(cardHolderName)
                || expirationDate == null || StringUtils.isBlank(cvv)) {
            throw new InvalidParameterException(
                    "All fields must be provided: card number, card holder name, expiration date, cvv");
        }
    }

    private void validateCardNumber(String cardNumber) {
        if (!creditCardValidator.isValid(cardNumber)) {
            throw new InvalidParameterException("Invalid card number");
        }
    }

    private void validateExpirationDate(YearMonth expirationDate) {
        YearMonth currentYearMonth = YearMonth.now();
        if (expirationDate.isBefore(currentYearMonth)) {
            throw new InvalidParameterException("Expired credit card");
        }
    }
}
