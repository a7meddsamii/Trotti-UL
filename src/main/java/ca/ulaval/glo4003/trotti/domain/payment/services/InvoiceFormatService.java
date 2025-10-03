package ca.ulaval.glo4003.trotti.domain.payment.services;

import ca.ulaval.glo4003.trotti.domain.order.Invoice;

public interface InvoiceFormatService<T> {

    T format(Invoice invoice);
}
