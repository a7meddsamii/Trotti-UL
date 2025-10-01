package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;

import java.time.LocalDate;
import java.util.*;

public final class Invoice {
    private final Id invoiceId;
    private final Id contextId;
    private final Idul buyerIdul;
    private final LocalDate issueDate;
    private final List<InvoiceLine> lines;

    private Invoice(Builder b) {
        this.invoiceId = Id.randomId();
        this.contextId = Objects.requireNonNull(b.orderId, "ID is required");
        this.buyerIdul = Objects.requireNonNull(b.buyer, "Buyer Idul is required");
        this.issueDate = Objects.requireNonNullElse(b.issueDate, LocalDate.now());
        this.lines     = List.copyOf(Objects.requireNonNull(b.lines, "lines"));

        if (lines.isEmpty()) throw new IllegalArgumentException("Invoice must have at least one line.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public Id getId() { return invoiceId; }
    public Id getContextId()   { return contextId; }
    public Idul getBuyerIdul()  { return buyerIdul; }
    public LocalDate getIssueDate() { return issueDate; }
    public List<InvoiceLine> getLines() { return List.copyOf(lines); }

    public static class Builder {
        private Id orderId;
        private Idul buyer;
        private List<InvoiceLine> lines = new ArrayList<>();
        private LocalDate issueDate;
        private Transaction transaction;

        public Builder id(Id id) {
            this.orderId = id;
            return this;
        }

        public Builder buyer(Idul buyer) {
            this.buyer = buyer; return this;
        }

        public Builder line(InvoiceLine line) {
            lines.add(line);
            return this;
        }

        public Builder issueDate(LocalDate date) {
            this.issueDate = date; return this;
        }

        public Builder transaction(Transaction transaction) {
            this.transaction = transaction;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
