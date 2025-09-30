package ca.ulaval.glo4003.trotti.domain.communication.strategies;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.communication.EmailStrategy;
import ca.ulaval.glo4003.trotti.domain.order.Invoice;

public class OrderInvoiceEmailStrategy implements EmailStrategy {
    private final Email buyerEmail;
    private final String buyerName;
    private final Invoice invoice;

    public OrderInvoiceEmailStrategy(Email buyerEmail, String buyerName, Invoice invoice) {
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.invoice = invoice;
    }

    @Override
    public Email getRecipient() {
        return buyerEmail;
    }

    @Override
    public String getSubject() {
        return "Invoice for Trotti-ul";
    }

    @Override
    public String getBody() {
        return invoice.toString(buyerEmail, buyerName);
    }
}
