package ca.ulaval.glo4003.trotti.application.order.mappers;

import ca.ulaval.glo4003.trotti.application.order.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.domain.payment.values.Transaction;

public class TransactionMapper {

    public TransactionDto toDto(Transaction t) {
        return new TransactionDto(t.getTransactionId(), t.getStatus(), t.getTimestamp(),
                t.getAmount(), t.getDescription());
    }
}
