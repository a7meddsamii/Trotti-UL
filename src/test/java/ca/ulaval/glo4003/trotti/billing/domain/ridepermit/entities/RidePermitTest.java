package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Currency;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RidePermitTest {

    private static final Duration DAILY_LIMIT = Duration.ofMinutes(60);
    private static final Duration A_TRAVEL_DURATION = Duration.ofMinutes(30);
	private static final Duration DIFFERENT_TRAVEL_DURATION = Duration.ofMinutes(50);
    private static final Duration ZERO_DURATION = Duration.ZERO;
	private static final LocalDateTime A_DATE_TIME = LocalDateTime.of(2025, 1, 1, 12, 0);

    private static final Money ZERO_CAD = Money.zeroCad();

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);

    private static final Idul IDUL = Idul.from("abcd");

    private Session session;
    private RidePermitId ridePermitId;
	private RidePermit ridePermit;

    @BeforeEach
    void setup() {
        session = Mockito.mock(Session.class);
        ridePermitId = RidePermitId.randomId();
		ridePermit = Mockito.spy(new RidePermit(ridePermitId, IDUL, session, DAILY_LIMIT));
    }
	
    @Test
    void givenEmptyAndNonEmptyUsages_whenGettingDailyBillingUsages_thenEmptyFilteredOut() {
        Map<LocalDate, DailyBillingUsage> initial = new HashMap<>();
        initial.put(TODAY, emptyUsage(DAILY_LIMIT, TODAY));
        initial.put(YESTERDAY, nonEmptyUsage(DAILY_LIMIT, YESTERDAY, A_TRAVEL_DURATION));
		ridePermit = new RidePermit(ridePermitId, IDUL, session, DAILY_LIMIT, initial);
		
        Map<LocalDate, DailyBillingUsage> usages = ridePermit.getDailyBillingUsages();
		
        assertEquals(1, usages.size());
        assertTrue(usages.containsKey(YESTERDAY));
        assertFalse(usages.containsKey(TODAY));
    }
	
    @Test
    void givenDateWithoutUsage_whenAddDailyTravelTime_thenUsageCreatedAndVisible() {
        ridePermit.addDailyTravelTime(A_DATE_TIME, A_TRAVEL_DURATION);
		
        Map<LocalDate, DailyBillingUsage> usages = ridePermit.getDailyBillingUsages();
		
        assertEquals(1, usages.size());
    }
	
    @Test
    void givenZeroDurationAdded_whenAddDailyTravelTime_thenUsageRemainsFilteredOut() {
        ridePermit.addDailyTravelTime(A_DATE_TIME, ZERO_DURATION);
        assertTrue(ridePermit.getDailyBillingUsages().isEmpty());
    }
	
    @Test
    void givenDate_whenAddDailyTravelTime_thenCorrectDailyBillingUsageIsUpdated(){
		ridePermit.addDailyTravelTime(A_DATE_TIME, A_TRAVEL_DURATION);
		ridePermit.addDailyTravelTime(A_DATE_TIME.plusDays(2), A_TRAVEL_DURATION);
		
		ridePermit.addDailyTravelTime(A_DATE_TIME, DIFFERENT_TRAVEL_DURATION);
		
		Mockito.verify(ridePermit).addDailyTravelTime(A_DATE_TIME, DIFFERENT_TRAVEL_DURATION);
	}
	
	private DailyBillingUsage nonEmptyUsage(Duration limit, LocalDate date, Duration traveled) {
		return new DailyBillingUsage(limit, date, traveled, ZERO_CAD, false);
	}
	
	private DailyBillingUsage emptyUsage(Duration limit, LocalDate date) {
		return new DailyBillingUsage(limit, date, ZERO_DURATION, ZERO_CAD, false);
	}
}