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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RidePermitTest {

    private static final Duration DAILY_LIMIT = Duration.ofMinutes(60);
    private static final Duration TRAVEL_DURATION_30 = Duration.ofMinutes(30);
    private static final Duration TRAVEL_DURATION_50 = Duration.ofMinutes(50);
    private static final Duration ZERO_DURATION = Duration.ZERO;
    private static final LocalDateTime VALID_DATE_TIME = LocalDateTime.of(2025, 1, 1, 12, 0);
    private static final RidePermitState VALID_STATE = RidePermitState.ACTIVE;
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
        initial.put(YESTERDAY, nonEmptyUsage(DAILY_LIMIT, YESTERDAY, TRAVEL_DURATION_30));
        ridePermit = new RidePermit(ridePermitId, IDUL, session, DAILY_LIMIT, initial, VALID_STATE);

        Map<LocalDate, DailyBillingUsage> usages = ridePermit.getDailyBillingUsages();

        Assertions.assertEquals(1, usages.size());
        Assertions.assertTrue(usages.containsKey(YESTERDAY));
        Assertions.assertFalse(usages.containsKey(TODAY));
    }

    @Test
    void givenDateWithoutUsage_whenAddDailyTravelTime_thenUsageCreatedAndVisible() {
        ridePermit.addDailyTravelTime(IDUL, VALID_DATE_TIME, TRAVEL_DURATION_30);

        Map<LocalDate, DailyBillingUsage> usages = ridePermit.getDailyBillingUsages();

        Assertions.assertEquals(1, usages.size());
    }

    @Test
    void givenZeroDurationAdded_whenAddDailyTravelTime_thenUsageRemainsFilteredOut() {
        ridePermit.addDailyTravelTime(IDUL, VALID_DATE_TIME, ZERO_DURATION);
        Assertions.assertTrue(ridePermit.getDailyBillingUsages().isEmpty());
    }

    @Test
    void givenDate_whenAddDailyTravelTime_thenCorrectDailyBillingUsageIsUpdated() {
        ridePermit.addDailyTravelTime(IDUL, VALID_DATE_TIME, TRAVEL_DURATION_30);
        ridePermit.addDailyTravelTime(IDUL, VALID_DATE_TIME.plusDays(2), TRAVEL_DURATION_30);

        ridePermit.addDailyTravelTime(IDUL, VALID_DATE_TIME, TRAVEL_DURATION_50);

        Mockito.verify(ridePermit).addDailyTravelTime(IDUL, VALID_DATE_TIME, TRAVEL_DURATION_50);
    }

    @Test
    void givenInvalidIdul_whenAddDailyTravelTime_thenThrowsException() {
        Idul invalidIdul = Idul.from("abcde");

        Executable addDailyTravelTime = () -> ridePermit.addDailyTravelTime(invalidIdul,
                VALID_DATE_TIME, TRAVEL_DURATION_30);

        Assertions.assertThrows(InvalidRidePermitOperation.class, addDailyTravelTime);
    }

    @Test
    void givenNegativeDuration_whenAddDailyTravelTime_thenThrowsException() {
        Duration negativeDuration = Duration.ofMinutes(-1);

        Executable addDailyTravelTime =
                () -> ridePermit.addDailyTravelTime(IDUL, VALID_DATE_TIME, negativeDuration);

        Assertions.assertThrows(InvalidRidePermitOperation.class, addDailyTravelTime);
    }

    @Test
    void givenRidePermitEligibleForActivation_whenActivate_thenRidePermitBecomesActiveAndReturnsTrue() {
        RidePermitState eligibleState = RidePermitState.INACTIVE;
        createRidePermitFromRidePermitState(eligibleState, session);

        boolean response = ridePermit.activate(session);

        RidePermitState expectedState = RidePermitState.ACTIVE;
        Assertions.assertEquals(expectedState, ridePermit.getPermitState());
        Assertions.assertTrue(response);
    }

    @Test
    void givenRidePermitIneligibleForActivation_whenActivate_thenRidePermitStaysInactiveAndReturnsFalse() {
        RidePermitState ineligibleState = RidePermitState.ACTIVE;
        createRidePermitFromRidePermitState(ineligibleState, session);

        boolean response = ridePermit.activate(session);

        RidePermitState expectedState = RidePermitState.ACTIVE;
        Assertions.assertEquals(expectedState, ridePermit.getPermitState());
        Assertions.assertFalse(response);
    }

    @Test
    void givenRidePermitIneligibleForActivationFromDifferentSessions_whenActivate_thenRidePermitStaysInactiveAndReturnsFalse() {
        RidePermitState ineligibleState = RidePermitState.INACTIVE;
        createRidePermitFromRidePermitState(ineligibleState, session);
        Session differentSession = Mockito.mock(Session.class);

        boolean response = ridePermit.activate(differentSession);

        RidePermitState expectedState = RidePermitState.INACTIVE;
        Assertions.assertEquals(expectedState, ridePermit.getPermitState());
        Assertions.assertFalse(response);
    }

    @Test
    void givenRidePermitEligibleForDeactivation_whenDeactivate_thenRidePermitBecomesExpiredAndReturnsTrue() {
        RidePermitState eligibleState = RidePermitState.ACTIVE;
        createRidePermitFromRidePermitState(eligibleState, session);

        boolean response = ridePermit.deactivate(session);

        RidePermitState expectedState = RidePermitState.EXPIRED;
        Assertions.assertEquals(expectedState, ridePermit.getPermitState());
        Assertions.assertTrue(response);
    }

    @Test
    void givenRidePermitIneligibleForDeactivation_whenDeactivate_thenRidePermitStaysActiveAndReturnsFalse() {
        RidePermitState ineligibleState = RidePermitState.INACTIVE;
        createRidePermitFromRidePermitState(ineligibleState, session);

        boolean response = ridePermit.deactivate(session);

        RidePermitState expectedState = RidePermitState.INACTIVE;
        Assertions.assertEquals(expectedState, ridePermit.getPermitState());
        Assertions.assertFalse(response);
    }

    @Test
    void givenRidePermitIneligibleForDeactivationFromDifferentSessions_whenDeactivate_thenRidePermitStaysActiveAndReturnsFalse() {
        RidePermitState ineligibleState = RidePermitState.ACTIVE;
        createRidePermitFromRidePermitState(ineligibleState, session);
        Session differentSession = Mockito.mock(Session.class);

        boolean response = ridePermit.deactivate(differentSession);

        RidePermitState expectedState = RidePermitState.ACTIVE;
        Assertions.assertEquals(expectedState, ridePermit.getPermitState());
        Assertions.assertFalse(response);
    }

    private void createRidePermitFromRidePermitState(RidePermitState ridePermitState) {
        createRidePermitFromRidePermitState(ridePermitState, session);
    }

    private void createRidePermitFromRidePermitState(RidePermitState ridePermitState,
            Session permitSession) {
        Map<LocalDate, DailyBillingUsage> initial = new HashMap<>();
        initial.put(TODAY, emptyUsage(DAILY_LIMIT, TODAY));
        initial.put(YESTERDAY, nonEmptyUsage(DAILY_LIMIT, YESTERDAY, TRAVEL_DURATION_30));
        ridePermit = new RidePermit(ridePermitId, IDUL, permitSession, DAILY_LIMIT, initial,
                ridePermitState);
    }

    private DailyBillingUsage nonEmptyUsage(Duration limit, LocalDate date, Duration traveled) {
        return new DailyBillingUsage(limit, date, traveled, ZERO_CAD, false);
    }

    private DailyBillingUsage emptyUsage(Duration limit, LocalDate date) {
        return new DailyBillingUsage(limit, date, ZERO_DURATION, ZERO_CAD, false);
    }
}
