package ca.ulaval.glo4003.trotti.trip.infrastructure.filter;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TripHistoryFilterTest {

    private static final Idul IDUL = Idul.from("IDUL");
    private static final Idul DIFFERENT_IDUL = Idul.from("IDUL2");
    private static final LocalDateTime TRIP_START_TIME = LocalDateTime.of(2024, 1, 10, 10, 0);
    private static final LocalDateTime TRIP_END_TIME = LocalDateTime.of(2024, 1, 10, 11, 0);
    private static final LocalDate CRITERIA_START_DATE = LocalDate.of(2024, 1, 1);
    private static final LocalDate CRITERIA_END_DATE = LocalDate.of(2024, 1, 31);
    private static final LocalDateTime INVALID_TRIP_START_TIME =
            LocalDateTime.of(2023, 12, 31, 23, 0);
    private static final LocalDateTime INVALID_TRIP_END_TIME = LocalDateTime.of(2024, 2, 1, 1, 0);

    private TripHistoryFilter filter;

    @BeforeEach
    void setUp() {
        filter = new TripHistoryFilter();
    }

    @Test
    void givenTripWithinDateRangeAndSameIdul_whenMatches_thenReturnsTrue() {
        TripRecord trip = mockTrip(IDUL, TRIP_START_TIME, TRIP_END_TIME);
        TripHistorySearchCriteria criteria =
                mockCriteria(IDUL, CRITERIA_START_DATE, CRITERIA_END_DATE);

        assertTrue(filter.matches(trip, criteria));
    }

    @Test
    void givenDifferentIdul_whenMatches_thenReturnsFalse() {
        TripRecord trip = mockTrip(IDUL, TRIP_START_TIME, TRIP_END_TIME);
        TripHistorySearchCriteria criteria =
                mockCriteria(DIFFERENT_IDUL, CRITERIA_START_DATE, CRITERIA_END_DATE);

        assertFalse(filter.matches(trip, criteria));
    }

    @Test
    void givenTripStartingBeforeCriteriaStart_whenMatches_thenReturnsFalse() {
        TripRecord trip = mockTrip(IDUL, INVALID_TRIP_START_TIME, TRIP_END_TIME);
        TripHistorySearchCriteria criteria =
                mockCriteria(IDUL, CRITERIA_START_DATE, CRITERIA_END_DATE);

        assertFalse(filter.matches(trip, criteria));
    }

    @Test
    void givenTripEndingAfterCriteriaEnd_whenMatches_thenReturnsFalse() {
        TripRecord trip = mockTrip(IDUL, TRIP_START_TIME, INVALID_TRIP_END_TIME);
        TripHistorySearchCriteria criteria =
                mockCriteria(IDUL, CRITERIA_START_DATE, CRITERIA_END_DATE);

        assertFalse(filter.matches(trip, criteria));
    }

    @Test
    void givenTripExactlyOnStartAndEndDates_whenMatches_thenReturnsTrue() {
        TripRecord trip = mockTrip(IDUL, TRIP_START_TIME, TRIP_START_TIME);
        TripHistorySearchCriteria criteria =
                mockCriteria(IDUL, CRITERIA_START_DATE, CRITERIA_END_DATE);

        assertTrue(filter.matches(trip, criteria));
    }

    private TripRecord mockTrip(Idul idul, LocalDateTime start, LocalDateTime end) {
        TripRecord trip = Mockito.mock(TripRecord.class);
        Mockito.when(trip.idul()).thenReturn(idul);
        Mockito.when(trip.startTime()).thenReturn(start);
        Mockito.when(trip.endTime()).thenReturn(end);
        return trip;
    }

    private TripHistorySearchCriteria mockCriteria(Idul idul, LocalDate start, LocalDate end) {
        TripHistorySearchCriteria criteria = Mockito.mock(TripHistorySearchCriteria.class);
        Mockito.when(criteria.getIdul()).thenReturn(idul);
        Mockito.when(criteria.getStartDate()).thenReturn(start);
        Mockito.when(criteria.getEndDate()).thenReturn(end);
        return criteria;
    }
}
