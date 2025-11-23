package ca.ulaval.glo4003.trotti.billing.domain.payment.factories;

import ca.ulaval.glo4003.trotti.billing.domain.payment.security.DataCodec;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.SecuredString;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.method.PaymentMethod;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.YearMonth;
import org.apache.commons.validator.routines.CreditCardValidator;

public class PaymentMethodFactory {

    private final CreditCardValidator creditCardValidator = new CreditCardValidator();
    private final DataCodec dataCodec;

    public PaymentMethodFactory(DataCodec dataCodec) {
        this.dataCodec = dataCodec;
    }

    public PaymentMethod createCreditCard(String cardNumber, String cardHolderName,
            YearMonth expirationDate) {
        validateCardNumber(cardNumber);
        validateExpirationDate(expirationDate);

        return CreditCard.from(SecuredString.fromPlain(cardNumber, dataCodec), cardHolderName,
                expirationDate);
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
