package ca.ulaval.glo4003.trotti.domain.communication.strategies;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.communication.EmailStrategy;
import ca.ulaval.glo4003.trotti.domain.order.Order;

public class OrderInvoiceEmailStrategy implements EmailStrategy {
    private final Email email;
    private final String name;
    private final Order order;

    public OrderInvoiceEmailStrategy(Email email, String name, Order order) {
        this.email = email;
        this.name = name;
        this.order = order;
    }

    @Override
    public Email getRecipient() {
        return email;
    }

    @Override
    public String getSubject() {
        return "Invoice for Trotti-ul order by " + name;
    }

    @Override
    public String getBody() {
        return order.getInvoice();
    }
}
