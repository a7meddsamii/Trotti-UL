package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.payment.exceptions.PaymentDeclinedException;

public abstract class PaymentMethod {

    public abstract void pay(Money amount) throws PaymentDeclinedException;
}
