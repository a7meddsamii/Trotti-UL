package ca.ulaval.glo4003.trotti.trip.domain.values;

import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TripHistorySearchCriteriaTest {
    public static final LocalDate A_START_DATE = LocalDate.of(2025, 1, 1);
    public static final LocalDate AN_END_DATE = LocalDate.of(2025, 1, 2);

    @Test
    void givenParameters_whenBuild_shouldReturnObjectWithSameValues() {
        TripHistorySearchCriteria criteria = TripHistorySearchCriteria.builder()
                .withIdul(AccountFixture.AN_IDUL)
                .withStartDate(A_START_DATE)
                .withEndDate(AN_END_DATE)
                .build();

        Assertions.assertEquals(AccountFixture.AN_IDUL, criteria.getIdul());
        Assertions.assertEquals(A_START_DATE, criteria.getStartDate());
        Assertions.assertEquals(AN_END_DATE, criteria.getEndDate());
    }

    @Test
    void givenNoIdul_whenBuild_shouldThrowException() {
        assertThrows(InvalidParameterException.class, () ->
                TripHistorySearchCriteria.builder()
                        .withStartDate(A_START_DATE)
                        .withEndDate(AN_END_DATE)
                        .build()
        );
    }
}
