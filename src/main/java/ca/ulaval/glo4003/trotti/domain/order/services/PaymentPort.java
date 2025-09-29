package ca.ulaval.glo4003.trotti.domain.order.services;

import ca.ulaval.glo4003.trotti.domain.commons.CreditCard;
import ca.ulaval.glo4003.trotti.domain.commons.Money;

public interface PaymentPort {
    void pay(CreditCard card, Money amount);
}
