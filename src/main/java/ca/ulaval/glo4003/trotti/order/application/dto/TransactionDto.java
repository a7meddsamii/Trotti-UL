package ca.ulaval.glo4003.trotti.order.application.dto;

import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import ca.ulaval.glo4003.trotti.payment.domain.values.transaction.TransactionId;
import ca.ulaval.glo4003.trotti.payment.domain.values.transaction.TransactionStatus;
import java.time.LocalDateTime;

public record TransactionDto(
        TransactionId id,
        TransactionStatus status,
        LocalDateTime timestamp,
        Money amount,
        String description
) {
}
