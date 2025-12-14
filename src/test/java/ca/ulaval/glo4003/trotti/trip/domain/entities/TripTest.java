package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TripException;
import ca.ulaval.glo4003.trotti.trip.domain.values.*;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

public class TripTest {

    public static final LocalDateTime START_TIME = LocalDateTime.of(2025, 1, 1, 10, 0);
    public static final long DURATION_MINUTES = 15L;
    public static final RidePermitId A_RIDE_PERMIT_ID = RidePermitId.randomId();
    public static final Idul A_TRAVELER_ID = Idul.from("travelerId");
    public static final ScooterId A_SCOOTER_ID = ScooterId.randomId();
    public static final Location A_START_LOCATION = Mockito.mock(Location.class);
    public static final LocalDateTime END_TIME = START_TIME.plusMinutes(DURATION_MINUTES);
    public static final Location A_END_LOCATION = Mockito.mock(Location.class);

    private Trip trip;

    @BeforeEach
    void setup() {
        trip = Trip.start(A_RIDE_PERMIT_ID, A_TRAVELER_ID, A_SCOOTER_ID, START_TIME,
                A_START_LOCATION);
    }

    @Test
    void whenStart_thenTripIsOngoing() {
        Trip trip = Trip.start(A_RIDE_PERMIT_ID, A_TRAVELER_ID, A_SCOOTER_ID, START_TIME,
                A_START_LOCATION);

        Assertions.assertEquals(TripStatus.ONGOING, trip.getStatus());
    }

    @Test
    void givenOngoingTrip_whenComplete_thenTripIsCompleted() {
        trip.complete(A_END_LOCATION, END_TIME);

        Assertions.assertEquals(TripStatus.COMPLETED, trip.getStatus());
    }

    @Test
    void givenOngoingTrip_whenComplete_thenTripEndLocationAndEndTimeAreSet() {
        trip.complete(A_END_LOCATION, END_TIME);

        Assertions.assertEquals(A_END_LOCATION, trip.getEndLocation());
        Assertions.assertEquals(END_TIME, trip.getEndTime());
    }

    @Test
    void givenCompletedTrip_whenComplete_thenThrowTripException() {
        trip.complete(A_END_LOCATION, END_TIME);

        Executable executable = () -> trip.complete(A_END_LOCATION, END_TIME);

        Assertions.assertThrows(TripException.class, executable);
    }

    @Test
    void givenOngoingTrip_whenCalculateDuration_thenThrowTripException() {
        Executable executable = () -> trip.calculateDuration();

        Assertions.assertThrows(TripException.class, executable);
    }

    @Test
    void givenCompletedTrip_whenCalculateDuration_thenReturnCorrectDuration() {
        trip.complete(A_END_LOCATION, END_TIME);

        Duration duration = trip.calculateDuration();

        Assertions.assertEquals(Duration.between(START_TIME, END_TIME), duration);
    }

    @Test
    void givenOnGoingTripAndInvalidEndTime_whenComplete_thenThrowTripException() {
        LocalDateTime invalidEndTime = START_TIME.minusMinutes(5);

        Executable executable = () -> trip.complete(A_END_LOCATION, invalidEndTime);

        Assertions.assertThrows(TripException.class, executable);
    }
}
