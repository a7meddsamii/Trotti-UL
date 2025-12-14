package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.dto;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitState;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;


public record RidePermitPersistenceDto(
        RidePermitId id,
        Idul riderId,
        Session session,
        Duration maximumTravelingTimePerDay,
        RidePermitState permitState,
        Map<LocalDate, DailyBillingUsagePersistenceDto> dailyBillingUsages
) {}
