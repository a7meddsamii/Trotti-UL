package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.dto;

import java.time.LocalDate;

public record DailyBillingUsagePersistenceDto(
        long maximumTravelingTimePerDayMinutes,
        LocalDate date,
        long travelingTimeMinutes,
        double balanceAmount,
        String balanceCurrency,
        boolean maximumTravelingTimeExceeded
) {
}
