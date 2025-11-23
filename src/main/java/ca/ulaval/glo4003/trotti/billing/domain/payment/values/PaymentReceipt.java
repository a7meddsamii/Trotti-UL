package ca.ulaval.glo4003.trotti.billing.domain.payment.values;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderId;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import java.util.Objects;

public final class PaymentReceipt {
    private final TransactionId transactionId;
    private final OrderId orderId;
    private final Money amountPaid;
    private final boolean success;

    private PaymentReceipt(
            TransactionId transactionId,
            OrderId orderId,
            Money amountPaid,
            boolean success) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.amountPaid = amountPaid;
        this.success = success;
    }

    public static PaymentReceipt of(TransactionId transactionId, OrderId orderId, Money amountPaid,
            boolean success) {
        return new PaymentReceipt(transactionId, orderId, amountPaid, success);
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
        if (this == o)
            return true;
        if (!(o instanceof PaymentReceipt other))
            return false;
        return success == other.success && transactionId.equals(other.transactionId)
                && amountPaid.equals(other.amountPaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, transactionId, amountPaid);
    }

    @Override
    public String toString() {
        return "PaymentReceipt{" + "success=" + success + ", transactionId=" + transactionId
                + ", amountPaid=" + amountPaid + '}';
    }
}
