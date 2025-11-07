package ca.ulaval.glo4003.trotti.payment.domain.entities.invoice;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.InvoiceId;
import ca.ulaval.glo4003.trotti.order.domain.values.OrderId;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Invoice {
    private final InvoiceId invoiceId;
    private final OrderId contextId;
    private final Idul buyerIdul;
    private final LocalDate issueDate;
    private final List<InvoiceLine> lines;
    private final Money totalAmount;

    private Invoice(Builder b) {
        this.invoiceId = InvoiceId.randomId();
        this.contextId = b.orderId;
        this.buyerIdul = b.buyer;
        this.issueDate = LocalDate.now();
        this.lines = List.copyOf(b.lines);
        this.totalAmount = b.totalAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public InvoiceId getId() {
        return invoiceId;
    }

    public OrderId getContextId() {
        return contextId;
    }

    public Idul getBuyerIdul() {
        return buyerIdul;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public List<InvoiceLine> getLines() {
        return List.copyOf(lines);
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public static class Builder {
        private OrderId orderId;
        private Idul buyer;
        private List<InvoiceLine> lines = new ArrayList<>();
        private Money totalAmount;

        public Builder id(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder buyer(Idul buyer) {
            this.buyer = buyer;
            return this;
        }

        public Builder line(InvoiceLine line) {
            lines.add(line);
            return this;
        }

        public Builder lines(List<InvoiceLine> lines) {
            if (lines == null) {
                return this;
            }
            this.lines.addAll(lines);
            return this;
        }

        public Builder totalAmount(Money amount) {
            this.totalAmount = amount;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
