package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalDateTime;

public class CompletedTripTest {

    public static final LocalDateTime A_START_TIME = LocalDateTime.of(2025, 1, 1, 10, 0);
    public static final LocalDateTime AN_END_TIME = LocalDateTime.of(2025, 1, 1, 10, 15);
    public static final Location A_START_LOCATION = Location.of("VACHON", "Station #1");
    public static final Location AN_END_LOCATION = Location.of("PEPS", "Station #1");

    public static final CompletedTrip A_COMPLETED_TRIP = new CompletedTrip(
            Mockito.mock(TripId.class),
            AccountFixture.AN_IDUL,
            A_START_TIME,
            A_START_LOCATION,
            AN_END_TIME,
            AN_END_LOCATION
    );

    @Test
    void givenStartTimeAndEndTime_whenCalculateDuration_thenCorrectlyCalculatesDuration() {
        Duration expectedDuration = Duration.between(A_START_TIME, AN_END_TIME);

        Duration duration = A_COMPLETED_TRIP.calculateDuration();

        Assertions.assertEquals(expectedDuration, duration);
    }
}