package ca.ulaval.glo4003.trotti.order.domain.services;

import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.Invoice;

public interface InvoiceFormatService<T> {

    T format(Invoice invoice);
}
