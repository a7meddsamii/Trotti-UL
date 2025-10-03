package ca.ulaval.glo4003.trotti.domain.payment.values;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import java.time.LocalDateTime;

public class Transaction {

    private final Id transactionId;
    private final TransactionStatus status;
    private final LocalDateTime timestamp;
    private final Money amount;
    private final String description;

    public Transaction(TransactionStatus status, Money amount, String description) {
        this.status = status;
        this.amount = amount;
        this.description = description;
        this.transactionId = Id.randomId();
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccessful() {
        return this.status == TransactionStatus.SUCCESS;
    }

    public Id getId() {
        return transactionId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Money getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
