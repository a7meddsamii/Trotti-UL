package ca.ulaval.glo4003.trotti.billing.domain.payment.values.method;

public interface PaymentMethod {
    boolean isType(PaymentMethodType type);

    boolean isEmpty();
}
