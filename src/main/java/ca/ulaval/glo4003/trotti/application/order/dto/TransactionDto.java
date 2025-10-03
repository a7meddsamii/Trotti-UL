package ca.ulaval.glo4003.trotti.application.order.dto;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.domain.payment.values.TransactionStatus;
import java.time.LocalDateTime;

public record TransactionDto(
        Id id,
        TransactionStatus status,
        LocalDateTime timestamp,
        Money amount,
        String description,
        String cardNumber
) {
}
