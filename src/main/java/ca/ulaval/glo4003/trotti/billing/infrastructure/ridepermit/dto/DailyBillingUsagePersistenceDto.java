package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.dto;

import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import java.time.Duration;
import java.time.LocalDate;

public record DailyBillingUsagePersistenceDto(
        Duration maximumTravelingTimePerDay,
        LocalDate date,
        Duration travelingTime,
        Money balance,
        boolean maximumTravelingTimeExceeded
) {
}
