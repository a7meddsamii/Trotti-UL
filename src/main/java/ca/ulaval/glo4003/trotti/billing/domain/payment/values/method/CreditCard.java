package ca.ulaval.glo4003.trotti.billing.domain.payment.values.method;

import ca.ulaval.glo4003.trotti.billing.domain.payment.values.SecuredString;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.billing.domain.payment.exceptions.InvalidPaymentMethodException;

import java.time.YearMonth;
import org.apache.commons.lang3.StringUtils;

public class CreditCard implements PaymentMethod {

    private final SecuredString cardNumber;
    private final String cardHolderName;
    private final YearMonth expiryDate;

    private CreditCard(SecuredString cardNumber, String cardHolderName, YearMonth expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
    }

    public static CreditCard from(SecuredString cardNumber, String cardHolderName,
            YearMonth expiryDate) {
        return new CreditCard(cardNumber, cardHolderName, expiryDate);
    }
	
	@Override
	public PaymentMethodType getType() {
		return PaymentMethodType.CREDIT_CARD;
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

    public boolean isExpired() {
        return expiryDate.isBefore(YearMonth.now());
    }
}
