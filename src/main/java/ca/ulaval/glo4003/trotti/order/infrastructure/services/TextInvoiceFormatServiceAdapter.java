package ca.ulaval.glo4003.trotti.order.infrastructure.services;

import ca.ulaval.glo4003.trotti.order.domain.services.InvoiceFormatService;
import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.Invoice;
import ca.ulaval.glo4003.trotti.payment.domain.entities.invoice.InvoiceLine;

public class TextInvoiceFormatServiceAdapter implements InvoiceFormatService<String> {

    @Override
    public String format(Invoice invoice) {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice ID: ").append(invoice.getId()).append("\n");
        sb.append("Order ID: ").append(invoice.getContextId()).append("\n");
        sb.append("Buyer: ").append(invoice.getBuyerIdul()).append("\n");
        sb.append("Issue Date: ").append(invoice.getIssueDate()).append("\n\n");

        sb.append("Items:\n");
        for (InvoiceLine line : invoice.getLines()) {
            sb.append(line.getDescription()).append("\n");
            sb.append("\n");
        }

        sb.append("Total: ").append(invoice.getTotalAmount()).append("\n");
        return sb.toString();
    }
}
