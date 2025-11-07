package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TripException;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class TripTest {
    private static final RidePermitId RIDE_PERMIT_ID = RidePermitId.randomId();
    private static final ScooterId SCOOTER_ID = ScooterId.randomId();
    private static final Idul AN_IDUL = Idul.from("abcd123");
    private static final LocalDateTime START_TIME = LocalDateTime.now();
    private static final LocalDateTime END_TIME = START_TIME.plusMinutes(1);

    private Trip trip;

    @BeforeEach
    public void setup() {
        trip = new Trip(START_TIME, RIDE_PERMIT_ID, AN_IDUL, SCOOTER_ID);
    }

    @Test
    void givenTripWithNoEndTime_whenEnd_thenReturnNewTripWithEndTime() {
        Trip trip = new Trip(START_TIME, RIDE_PERMIT_ID, AN_IDUL, SCOOTER_ID);

        Trip result = trip.end(END_TIME);

        Assertions.assertEquals(END_TIME, result.getEndTime());
        Assertions.assertNull(trip.getEndTime());
    }

    @Test
    void givenTripThatEnded_whenEnd_thenThrowsTripException() {
        Trip endedTrip = trip.end(END_TIME);

        Executable endingTrip = () -> endedTrip.end(END_TIME);

        Assertions.assertThrows(TripException.class, endingTrip);
    }

    @Test
    void givenEndTimeBeforeStartTime_whenEnd_thenThrowsTripException() {
        LocalDateTime timeBeforeStart = START_TIME.minusMinutes(1);

        Executable endingTrip = () -> trip.end(timeBeforeStart);

        Assertions.assertThrows(TripException.class, endingTrip);
    }

}
