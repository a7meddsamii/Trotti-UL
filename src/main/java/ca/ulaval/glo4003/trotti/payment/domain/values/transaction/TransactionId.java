package ca.ulaval.glo4003.trotti.payment.domain.values.transaction;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Id;

public class TransactionId extends Id {

    private TransactionId() {
        super();
    }

    private TransactionId(String value) {
        super(value);
    }

    public static TransactionId from(String value) {
        return new TransactionId(value);
    }

    public static TransactionId randomId() {
        return new TransactionId();
    }
}
