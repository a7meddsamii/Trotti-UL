package ca.ulaval.glo4003.trotti.billing.domain.payment.values.method;

public class EmptyPaymentMethod implements PaymentMethod {
    @Override
    public boolean isType(PaymentMethodType type) {
        return type == PaymentMethodType.EMPTY;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
