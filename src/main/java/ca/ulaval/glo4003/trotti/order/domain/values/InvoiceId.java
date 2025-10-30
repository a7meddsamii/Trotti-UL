package ca.ulaval.glo4003.trotti.order.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.Id;

public class InvoiceId extends Id {

    private InvoiceId() {
        super();
    }

    private InvoiceId(String value) {
        super(value);
    }

    public static InvoiceId from(String value) {
        return new InvoiceId(value);
    }

    public static InvoiceId randomId() {
        return new InvoiceId();
    }
}
