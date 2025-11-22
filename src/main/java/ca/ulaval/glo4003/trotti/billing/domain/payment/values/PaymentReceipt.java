package ca.ulaval.glo4003.trotti.billing.domain.payment.values;

import java.util.Objects;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;

public final class PaymentReceipt {
    private final boolean success;
    private final TransactionId transactionId;
    private final Money amountPaid;

    public PaymentReceipt(boolean success, TransactionId transactionId, Money amountPaid) {
        this.success = success;
        this.transactionId = Objects.requireNonNull(transactionId, "transactionId must not be null");
        this.amountPaid = Objects.requireNonNull(amountPaid, "amountPaid must not be null");
    }

    public static PaymentReceipt of(boolean success, TransactionId transactionId, Money amountPaid) {
        return new PaymentReceipt(success, transactionId, amountPaid);
    }

    public boolean isSuccess() {
        return success;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public Money getAmountPaid() {
        return amountPaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentReceipt other)) return false;
        return success == other.success
            && transactionId.equals(other.transactionId)
            && amountPaid.equals(other.amountPaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, transactionId, amountPaid);
    }

    @Override
    public String toString() {
        return "PaymentReceipt{" +
            "success=" + success +
            ", transactionId=" + transactionId +
            ", amountPaid=" + amountPaid +
            '}';
    }
}