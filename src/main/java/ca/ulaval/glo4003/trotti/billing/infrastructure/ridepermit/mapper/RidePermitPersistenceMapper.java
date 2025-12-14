package ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.mapper;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Semester;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Currency;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.DailyBillingUsage;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitState;
import ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.dto.DailyBillingUsagePersistenceDto;
import ca.ulaval.glo4003.trotti.billing.infrastructure.ridepermit.dto.RidePermitPersistenceDto;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RidePermitPersistenceMapper {
    public RidePermitPersistenceDto toDto(RidePermit ridePermit) {
        Map<LocalDate, DailyBillingUsagePersistenceDto> usageDtos = new HashMap<>();
        ridePermit.getDailyBillingUsages().forEach((date, usage) -> {
            usageDtos.put(date, toUsageDto(usage));
        });

        return new RidePermitPersistenceDto(ridePermit.getId(),
                ridePermit.getRiderId(),
                ridePermit.getSession(),
                ridePermit.getMaximumTravelingTimePerDay(),
                ridePermit.getPermitState(),
                usageDtos
        );
    }

    public RidePermit toDomain(RidePermitPersistenceDto dto) {
            Map<LocalDate, DailyBillingUsage> dailyUsages = new HashMap<>();
            dto.dailyBillingUsages().forEach((date, usageDto) -> {
                dailyUsages.put(date, toUsageDomain(usageDto));
            });

            return new RidePermit(
                    dto.id(),
                    dto.riderId(),
                    dto.session(),
                    dto.maximumTravelingTimePerDay(),
                    dailyUsages,
                    dto.permitState()
            );
    }

    private DailyBillingUsagePersistenceDto toUsageDto(DailyBillingUsage usage) {
        return new DailyBillingUsagePersistenceDto(
                usage.getMaximumTravelingTimePerDay(),
                usage.getDate(),
                usage.getTravelingTime(),
                usage.getBalance(),
                usage.isMaximumTravelingTimeExceeded()
        );
    }

    private DailyBillingUsage toUsageDomain(DailyBillingUsagePersistenceDto dto) {
        return new DailyBillingUsage(
                dto.maximumTravelingTimePerDay(),
                dto.date(),
                dto.travelingTime(),
                dto.balance(),
                dto.maximumTravelingTimeExceeded()
        );
    }
}
