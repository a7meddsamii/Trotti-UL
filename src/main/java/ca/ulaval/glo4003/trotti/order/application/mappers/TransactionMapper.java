package ca.ulaval.glo4003.trotti.order.application.mappers;

import ca.ulaval.glo4003.trotti.order.application.dto.TransactionDto;
import ca.ulaval.glo4003.trotti.payment.domain.values.transaction.Transaction;

public class TransactionMapper {

    public TransactionDto toDto(Transaction t) {
        return new TransactionDto(t.getId(), t.getStatus(), t.getTimestamp(), t.getAmount(),
                t.getDescription());
    }
}
