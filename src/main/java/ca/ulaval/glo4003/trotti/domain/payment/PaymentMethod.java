package ca.ulaval.glo4003.trotti.domain.payment;

import ca.ulaval.glo4003.trotti.domain.payment.values.Money;

public interface PaymentMethod {

    void pay(Money amount);

    boolean isExpired();
}
