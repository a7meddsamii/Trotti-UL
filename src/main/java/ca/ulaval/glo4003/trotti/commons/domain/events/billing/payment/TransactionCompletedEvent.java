package ca.ulaval.glo4003.trotti.payment.domain.events;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class TransactionCompletedEvent extends Event {

    private final String transactionId;
    private final String transactionStatus;
    private final String transactionDescription;

    public TransactionCompletedEvent(
            Idul idul,
            String transactionId,
            String transactionStatus,
            String transactionDescription) {
        super(idul, "transaction." + transactionStatus.toLowerCase());
        this.transactionId = transactionId;
        this.transactionStatus = transactionStatus;
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
