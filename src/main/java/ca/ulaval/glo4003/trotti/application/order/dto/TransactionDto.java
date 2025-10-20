package ca.ulaval.glo4003.trotti.application.order.dto;

import ca.ulaval.glo4003.trotti.domain.commons.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.TransactionId;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.transaction.TransactionStatus;
import java.time.LocalDateTime;

public record TransactionDto(
        TransactionId id,
        TransactionStatus status,
        LocalDateTime timestamp,
        Money amount,
        String description
) {
}
