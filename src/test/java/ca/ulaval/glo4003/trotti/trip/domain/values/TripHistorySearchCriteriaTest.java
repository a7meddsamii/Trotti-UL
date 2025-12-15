package ca.ulaval.glo4003.trotti.trip.domain.values;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class TripHistorySearchCriteriaTest {

    private static final Idul VALID_IDUL = Idul.from("CM1B2G45");
    private static final LocalDate VALID_START_DATE = LocalDate.of(2024, 1, 1);
    private static final LocalDate VALID_END_DATE = LocalDate.of(2024, 1, 31);
    private static final LocalDate INVALID_START_DATE = LocalDate.of(2024, 2, 1);

    @Test
    void givenValidParameters_whenBuild_thenCriteriaIsCreated() {
        TripHistorySearchCriteria criteria =
                TripHistorySearchCriteria.builder().withIdul(VALID_IDUL)
                        .withStartDate(VALID_START_DATE).withEndDate(VALID_END_DATE).build();

        assertEquals(VALID_IDUL, criteria.getIdul());
        assertEquals(VALID_START_DATE, criteria.getStartDate());
        assertEquals(VALID_END_DATE, criteria.getEndDate());
    }

    @Test
    void givenNoIdul_whenBuild_thenThrowsException() {
        TripHistorySearchCriteria.Builder builder = TripHistorySearchCriteria.builder()
                .withStartDate(VALID_START_DATE).withEndDate(VALID_END_DATE);

        assertThrows(InvalidParameterException.class, builder::build);
    }

    @Test
    void givenStartDateAfterEndDate_whenBuild_thenThrowsInvalidParameterException() {
        TripHistorySearchCriteria.Builder builder = TripHistorySearchCriteria.builder()
                .withIdul(VALID_IDUL).withStartDate(INVALID_START_DATE).withEndDate(VALID_END_DATE);

        assertThrows(InvalidParameterException.class, builder::build);
    }

    @Test
    void givenOnlyStartDate_whenBuild_thenEndDateIsComputed() {
        LocalDate expectedEndDate = VALID_START_DATE.plusMonths(1).minusDays(1);

        TripHistorySearchCriteria criteria = TripHistorySearchCriteria.builder()
                .withIdul(VALID_IDUL).withStartDate(VALID_START_DATE).build();

        assertEquals(VALID_START_DATE, criteria.getStartDate());
        assertEquals(expectedEndDate, criteria.getEndDate());
    }

    @Test
    void givenOnlyEndDate_whenBuild_thenStartDateIsComputed() {
        LocalDate expectedStartDate = VALID_END_DATE.minusMonths(1).plusDays(1);

        TripHistorySearchCriteria criteria = TripHistorySearchCriteria.builder()
                .withIdul(VALID_IDUL).withEndDate(VALID_END_DATE).build();

        assertEquals(expectedStartDate, criteria.getStartDate());
        assertEquals(VALID_END_DATE, criteria.getEndDate());
    }

    @Test
    void givenNoStartDateAndNoEndDate_whenBuild_thenDefaultsToPreviousMonth() {
        LocalDate expectedStartDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate expectedEndDate =
                expectedStartDate.withDayOfMonth(expectedStartDate.lengthOfMonth());

        TripHistorySearchCriteria criteria =
                TripHistorySearchCriteria.builder().withIdul(VALID_IDUL).build();

        assertEquals(expectedStartDate, criteria.getStartDate());
        assertEquals(expectedEndDate, criteria.getEndDate());
    }
}
