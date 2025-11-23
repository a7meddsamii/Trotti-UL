package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.exceptions.InvalidRidePermitOperation;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitState;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RidePermit {
    private final RidePermitId id;
    private final Idul riderId;
    private final Session session;
    private final Duration maximumTravelingTimePerDay;
    private final Map<LocalDate, DailyBillingUsage> dailyBillingUsages;
    private RidePermitState permitState;

    public RidePermit(
            RidePermitId id,
            Idul riderId,
            Session session,
            Duration maximumTravelingTimePerDay) {
        this(id, riderId, session, maximumTravelingTimePerDay, new HashMap<>(),
                RidePermitState.INACTIVE);
    }

    public RidePermit(
            RidePermitId id,
            Idul riderId,
            Session session,
            Duration maximumTravelingTimePerDay,
            Map<LocalDate, DailyBillingUsage> dailyBillingUsages,
            RidePermitState permitState) {
        this.id = id;
        this.riderId = riderId;
        this.session = session;
        this.maximumTravelingTimePerDay = maximumTravelingTimePerDay;
        this.dailyBillingUsages = new HashMap<>(dailyBillingUsages);
        this.permitState = permitState;
    }

    public void addDailyTravelTime(Idul riderId, LocalDateTime startDateTime, Duration travelTime) {
        if (!this.riderId.equals(riderId)) {
            throw new InvalidRidePermitOperation(
                    "Rider " + riderId + " does not own this ride permit");
        }

        if (travelTime.isNegative()) {
            throw new InvalidRidePermitOperation("Travel time cannot be negative");
        }

        LocalDate date = startDateTime.toLocalDate();
        DailyBillingUsage dailyUsage = getDailyBillingUsage(date);
        dailyUsage.addTravelTime(travelTime);
    }

    public Map<LocalDate, DailyBillingUsage> getDailyBillingUsages() {
        Map<LocalDate, DailyBillingUsage> copyOfDailyUsages = new HashMap<>();

        for (DailyBillingUsage dailyUsage : dailyBillingUsages.values()) {
            if (!dailyUsage.isEmpty()) {
                copyOfDailyUsages.put(dailyUsage.getDate(), dailyUsage);
            }
        }

        return copyOfDailyUsages;
    }

    public boolean activate(Session currentSchoolSession) {
        boolean eligibleForActivation = this.permitState == RidePermitState.INACTIVE
                && this.session.equals(currentSchoolSession);
        if (!eligibleForActivation) {
            return false;
        }

        this.permitState = RidePermitState.ACTIVE;
        return true;
    }

    public boolean deactivate(Session currentSchoolSession) {
        boolean eligibleForDeactivation = this.permitState == RidePermitState.ACTIVE
                && this.session.equals(currentSchoolSession);
        if (!eligibleForDeactivation) {
            return false;
        }

        this.permitState = RidePermitState.EXPIRED;
        return true;
    }
	
	public Money getBalance() {
		Money totalBalance = Money.zeroCad();
		
		for (DailyBillingUsage dailyUsage : dailyBillingUsages.values()) {
			totalBalance = totalBalance.plus(dailyUsage.getBalance());
		}
		
		return totalBalance;
	}

    public boolean isActiveForRides(Idul riderId, LocalDate date) {
        return this.riderId.equals(riderId) && this.session.contains(date) && this.permitState == RidePermitState.ACTIVE;
    }

    public Duration getMaximumTravelingTimePerDay() {
        return maximumTravelingTimePerDay;
    }

    public RidePermitId getId() {
        return id;
    }

    public Idul getRiderId() {
        return riderId;
    }

    public Session getSession() {
        return session;
    }

    public RidePermitState getPermitState() {
        return permitState;
    }

    private DailyBillingUsage getDailyBillingUsage(LocalDate date) {
        return dailyBillingUsages.computeIfAbsent(date,
                givenDate -> new DailyBillingUsage(maximumTravelingTimePerDay, givenDate,
                        Duration.ZERO, Money.zeroCad(), false));
    }
}
