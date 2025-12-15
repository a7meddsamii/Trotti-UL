package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TripHistoryTest {
    private static final LocalDateTime ANOTHER_ENDTIME_TIME = LocalDateTime.of(2025, 1, 1, 10, 6);
    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 1, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 1, 1, 10, 15);
    private static final Location START_LOCATION = Location.of("VACHON", "Station #1");
    private static final Location END_LOCATION = Location.of("PEPS", "Station #1");
    private static final CompletedTrip COMPLETED_TRIP =
            new CompletedTrip(Mockito.mock(TripId.class), AccountFixture.IDUL, START_TIME,
                    START_LOCATION, END_TIME, END_LOCATION);
    private static final CompletedTrip ANOTHER_COMPLETED_TRIP =
            new CompletedTrip(Mockito.mock(TripId.class), AccountFixture.IDUL, START_TIME,
                    END_LOCATION, ANOTHER_ENDTIME_TIME, START_LOCATION);
    private static final List<CompletedTrip> COMPLETED_TRIP_LIST = new ArrayList<CompletedTrip>();
    private static final List<CompletedTrip> EMPTY_COMPLETED_TRIP_LIST =
            new ArrayList<CompletedTrip>();

    private TripHistory tripHistory;
    private TripHistory emptyTripHistory;

    @BeforeEach
    void setUp() {
        COMPLETED_TRIP_LIST.clear();
        EMPTY_COMPLETED_TRIP_LIST.clear();
        COMPLETED_TRIP_LIST.add(COMPLETED_TRIP);
        COMPLETED_TRIP_LIST.add(COMPLETED_TRIP);
        COMPLETED_TRIP_LIST.add(ANOTHER_COMPLETED_TRIP);
        tripHistory = new TripHistory(COMPLETED_TRIP_LIST);
        emptyTripHistory = new TripHistory(EMPTY_COMPLETED_TRIP_LIST);
    }

    @Test
    void givenCompletedTripList_whenCalculateTotalTripsDuration_thenReturnExpectedDuration() {
        Duration expectedDuration = Duration.ofMinutes(36);

        Duration duration = tripHistory.calculateTotalTripsDuration();

        Assertions.assertEquals(expectedDuration, duration);
    }

    @Test
    void givenCompletedTripList_whenCalculateNumberOfTrips_thenReturnExpectedDuration() {
        int expectedNumberOfTrips = 3;

        int numberOfTrips = tripHistory.calculateNumberOfTrips();

        Assertions.assertEquals(expectedNumberOfTrips, numberOfTrips);
    }

    @Test
    void givenCompletedTripList_whenGetFavoriteStartLocation_thenReturnExpectedLocation() {
        Location expectedLocation = START_LOCATION;

        Location location = tripHistory.getFavoriteStartLocation();

        Assertions.assertEquals(expectedLocation, location);
    }

    @Test
    void givenCompletedTripList_whenGetFavoriteEndLocation_thenReturnExpectedLocation() {
        Location expectedLocation = END_LOCATION;

        Location location = tripHistory.getFavoriteEndLocation();

        Assertions.assertEquals(expectedLocation, location);
    }

    @Test
    void givenEmptyCompletedTripList_whenCalculateTotalTripsDuration_thenReturnExpectedDuration() {
        Duration expectedDuration = Duration.ofMinutes(0);

        Duration duration = emptyTripHistory.calculateTotalTripsDuration();

        Assertions.assertEquals(expectedDuration, duration);
    }

    @Test
    void givenEmptyCompletedTripList_whenCalculateNumberOfTrips_thenReturnExpectedDuration() {
        int expectedNumberOfTrips = 0;

        int numberOfTrips = emptyTripHistory.calculateNumberOfTrips();

        Assertions.assertEquals(expectedNumberOfTrips, numberOfTrips);
    }

    @Test
    void givenEmptyCompletedTripList_whenGetFavoriteStartLocation_thenReturnNull() {
        Location location = emptyTripHistory.getFavoriteStartLocation();

        Assertions.assertNull(location);
    }

    @Test
    void givenEmptyCompletedTripList_whenGetFavoriteEndLocation_thenReturnNull() {
        Location location = emptyTripHistory.getFavoriteEndLocation();

        Assertions.assertNull(location);
    }
}
