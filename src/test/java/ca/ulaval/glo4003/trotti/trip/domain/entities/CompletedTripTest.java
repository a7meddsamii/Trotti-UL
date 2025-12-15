package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CompletedTripTest {

    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 1, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 1, 1, 10, 15);
    private static final Location START_LOCATION = Location.of("VACHON", "Station #1");
    private static final Location END_LOCATION = Location.of("PEPS", "Station #1");

    private static final CompletedTrip A_COMPLETED_TRIP =
            new CompletedTrip(Mockito.mock(TripId.class), AccountFixture.IDUL, START_TIME,
                    START_LOCATION, END_TIME, END_LOCATION);

    @Test
    void givenStartTimeAndEndTime_whenCalculateDuration_thenCorrectlyCalculatesDuration() {
        Duration expectedDuration = Duration.between(START_TIME, END_TIME);

        Duration duration = A_COMPLETED_TRIP.calculateDuration();

        Assertions.assertEquals(expectedDuration, duration);
    }
}
