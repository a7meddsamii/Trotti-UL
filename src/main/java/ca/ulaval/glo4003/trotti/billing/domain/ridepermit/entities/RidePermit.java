package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

public class RidePermit {
	private final RidePermitId id;
	private final Idul idul;
	private final Session session;
	private final Duration maximumTravelingTimePerDay;
	private final Map<LocalDate, DailyBillingUsage> dailyBillingUsages;
	
	public RidePermit(RidePermitId id, Idul idul, Session session, Duration maximumTravelingTimePerDay) {
		this(id, idul, session, maximumTravelingTimePerDay, new HashMap<>());
	}
	
	public RidePermit(RidePermitId id, Idul idul, Session session, Duration maximumTravelingTimePerDay, Map<LocalDate, DailyBillingUsage> dailyBillingUsages) {
		this.id = id;
		this.idul = idul;
		this.session = session;
		this.maximumTravelingTimePerDay = maximumTravelingTimePerDay;
		this.dailyBillingUsages = new HashMap<>(dailyBillingUsages);
	}
	
	public void addDailyTravelTime(LocalDateTime startDateTime, Duration travelTime) {
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
	
	public Duration getMaximumTravelingTimePerDay() {
		return maximumTravelingTimePerDay;
	}
	
	public RidePermitId getId() {
		return id;
	}
	
	public Idul getIdul() {
		return idul;
	}
	
	public Session getSession() {
		return session;
	}
	
	private DailyBillingUsage getDailyBillingUsage(LocalDate date) {
		 return dailyBillingUsages.computeIfAbsent(date, this::startDailyBillingUsage);
	}
	
	private DailyBillingUsage startDailyBillingUsage(LocalDate date) {
		DailyBillingUsage newDailyUsage = new DailyBillingUsage(maximumTravelingTimePerDay, date, Duration.ZERO, Money.zeroCad(), false);
		dailyBillingUsages.put(date, newDailyUsage);
		
		return newDailyUsage;
	}
}