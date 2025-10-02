package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class Invoice {
    private final Id invoiceId;
    private final Id contextId;
    private final Idul buyerIdul;
    private final LocalDate issueDate;
    private final List<InvoiceLine> lines;
    private final Money totalAmount;

    private Invoice(Builder b) {
        this.invoiceId = Id.randomId();
        this.contextId = b.orderId;
        this.buyerIdul = b.buyer;
        this.issueDate = b.issueDate;
        this.lines = List.copyOf(b.lines);
        this.totalAmount = b.totalAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Id getId() {
        return invoiceId;
    }

    public Id getContextId() {
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
        private Id orderId;
        private Idul buyer;
        private List<InvoiceLine> lines = new ArrayList<>();
        private LocalDate issueDate = LocalDate.now();
        private Money totalAmount;

        public Builder id(Id id) {
            this.orderId = id;
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

        public Builder totalAmount(Money amount) {
            this.totalAmount = amount;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
