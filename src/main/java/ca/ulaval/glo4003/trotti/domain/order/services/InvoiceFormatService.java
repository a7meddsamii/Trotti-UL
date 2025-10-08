package ca.ulaval.glo4003.trotti.domain.order.services;

import ca.ulaval.glo4003.trotti.domain.order.entities.invoice.Invoice;

public interface InvoiceFormatService<T> {

    T format(Invoice invoice);
}
