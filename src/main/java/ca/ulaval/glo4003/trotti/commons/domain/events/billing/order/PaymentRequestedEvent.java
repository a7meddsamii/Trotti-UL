package ca.ulaval.glo4003.trotti.commons.domain.events.billing.order;

import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class PaymentRequestedEvent extends Event {
    String orderId;
    String paymentMethod;
    Money totalPrice;

    protected PaymentRequestedEvent(Idul idul) {
        super(idul, "order.payment.requested");
    }
}
