package ca.ulaval.glo4003.trotti.domain.trip.entities;


import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.trip.exceptions.TripException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

class TripTest {
    private static final Id AN_ID = Id.randomId();
    private static final Idul AN_IDUL = Idul.from("abcd123");
    private static final LocalDateTime START_TIME = LocalDateTime.now();
    private static final LocalDateTime END_TIME = LocalDateTime.now();


    private Trip trip;


    @BeforeEach
    public void setup() {
        trip = new Trip(START_TIME,AN_ID,AN_IDUL,AN_ID);
    }

    @Test
    void givenTripWithNoEndTime_whenEnd_thenReturnNewTripWithEndTime(){
        Trip trip = new Trip(START_TIME,AN_ID,AN_IDUL,AN_ID);

        Trip result = trip.end(END_TIME);

        Assertions.assertEquals(END_TIME, result.getEndTime());
        Assertions.assertNull(trip.getEndTime());
    }

    @Test
    void givenTripThatEnded_whenEnd_thenThrowsTripException(){
        Trip endedTrip = trip.end(END_TIME);

        Executable endingTrip = () ->  endedTrip.end(END_TIME);

        Assertions.assertThrows(TripException.class,endingTrip);
    }

    @Test
    void givenEndTimeBeforeStartTime_whenEnd_thenThrowsTripException(){
        LocalDateTime timeBeforeStart = START_TIME.minusMinutes(1);

        Executable endingTrip = () -> trip.end(timeBeforeStart);

        Assertions.assertThrows(TripException.class,endingTrip);
    }

}