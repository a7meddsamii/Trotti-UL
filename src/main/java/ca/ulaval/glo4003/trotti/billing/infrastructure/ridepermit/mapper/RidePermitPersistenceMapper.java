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
        Map<String, DailyBillingUsagePersistenceDto> usageDtos = new HashMap<>();
        ridePermit.getDailyBillingUsages().forEach((date, usage) -> {
            usageDtos.put(date.toString(), toUsageDto(usage));
        });

        return new RidePermitPersistenceDto(
                ridePermit.getId().toString(),
                ridePermit.getRiderId().toString(),
                String.valueOf(ridePermit.getSession().getSemester().getCode()),
                ridePermit.getSession().getStartDate(),
                ridePermit.getSession().getEndDate(),
                ridePermit.getMaximumTravelingTimePerDay().toMinutes(),
                ridePermit.getPermitState().name(),
                usageDtos
        );
    }

    public RidePermit toDomain(RidePermitPersistenceDto dto) {
        RidePermitId id = RidePermitId.from(dto.id());
        Idul riderId = Idul.from(dto.riderId());
        Session session = new Session(
                Semester.fromString(dto.semesterCode()),
                dto.sessionStartDate(),
                dto.sessionEndDate()
        );
        Duration maximumTravelingTimePerDay = Duration.ofMinutes(dto.maximumTravelingTimePerDayMinutes());

        Map<LocalDate, DailyBillingUsage> dailyUsages = new HashMap<>();
        dto.dailyBillingUsages().forEach((dateStr, usageDto) -> {
            LocalDate date = LocalDate.parse(dateStr);
            dailyUsages.put(date, toUsageDomain(usageDto));
        });

        RidePermitState state = RidePermitState.valueOf(dto.permitState());

        return new RidePermit(id, riderId, session, maximumTravelingTimePerDay, dailyUsages, state);
    }

    private DailyBillingUsagePersistenceDto toUsageDto(DailyBillingUsage usage) {
        return new DailyBillingUsagePersistenceDto(
                usage.getMaximumTravelingTimePerDay().toMinutes(),
                usage.getDate(),
                usage.getTravelingTime().toMinutes(),
                usage.getBalance().getAmount(),
                usage.getBalance().getCurrency().name(),
                usage.isMaximumTravelingTimeExceeded()
        );
    }

    private DailyBillingUsage toUsageDomain(DailyBillingUsagePersistenceDto dto) {
        Duration maxTime = Duration.ofMinutes(dto.maximumTravelingTimePerDayMinutes());
        Duration travelingTime = Duration.ofMinutes(dto.travelingTimeMinutes());
        Money balance = Money.of(dto.balanceAmount(), Currency.valueOf(dto.balanceCurrency()));

        return new DailyBillingUsage(
                maxTime,
                dto.date(),
                travelingTime,
                balance,
                dto.maximumTravelingTimeExceeded()
        );
    }
}
