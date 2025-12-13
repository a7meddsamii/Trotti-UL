package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.dto;

import java.time.LocalDate;
import java.util.Map;


public record RidePermitPersistenceDto(
        String id,
        String riderId,
        String semesterCode,
        LocalDate sessionStartDate,
        LocalDate sessionEndDate,
        long maximumTravelingTimePerDayMinutes,
        String permitState,
        Map<String, DailyBillingUsagePersistenceDto> dailyBillingUsages
) {
}
