package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.payment.values.Money;

public abstract class PaymentMethod {

    public abstract void pay(Money amount);

    public abstract boolean isExpired();

    public abstract String generateInvoice();
}
