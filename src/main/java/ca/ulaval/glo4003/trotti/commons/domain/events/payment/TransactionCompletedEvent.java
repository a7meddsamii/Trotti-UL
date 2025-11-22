package ca.ulaval.glo4003.trotti.commons.domain.events.payment;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import java.time.LocalDateTime;

public class TransactionCompletedEvent extends Event {

    private final String transactionId;
    private final String transactionStatus;
    private final String paymentMethod;
    private final Money amount;
    private final String transactionDescription;
    private final LocalDateTime transactionTimestamp;

    public TransactionCompletedEvent(
            Idul idul,
            String transactionId,
            String transactionStatus,
            String paymentMethod,
            Money amount,
            String transactionDescription,
            LocalDateTime transactionTimestamp) {
        super(idul, "transaction." + transactionStatus.toLowerCase());
        this.transactionId = transactionId;
        this.transactionStatus = transactionStatus;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.transactionDescription = transactionDescription;
        this.transactionTimestamp = transactionTimestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Money getAmount() {
        return amount;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public LocalDateTime getTransactionTimestamp() {
        return transactionTimestamp;
    }
}
