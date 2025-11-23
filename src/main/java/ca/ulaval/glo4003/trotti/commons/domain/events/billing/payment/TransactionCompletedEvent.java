package ca.ulaval.glo4003.trotti.commons.domain.events.billing.payment;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class TransactionCompletedEvent extends Event {

    private final String transactionId;
    private final String transactionStatus;
    private final String transactionDescription;

    public TransactionCompletedEvent(
            Idul idul,
            String transactionId,
            boolean isSuccessful,
            String transactionDescription) {
        super(idul, "transaction." + (isSuccessful? "success" : "failed"));
        this.transactionId = transactionId;
        this.transactionStatus = (isSuccessful? "success" : "failed");
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }
}
