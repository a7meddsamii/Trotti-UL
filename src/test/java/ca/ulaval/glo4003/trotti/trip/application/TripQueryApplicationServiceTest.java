package ca.ulaval.glo4003.trotti.trip.application;

import ca.ulaval.glo4003.trotti.trip.domain.entities.TripHistory;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TripQueryRepository;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripHistorySearchCriteria;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TripQueryApplicationServiceTest {

    private TripQueryApplicationService service;
    private TripQueryRepository tripQueryRepository;

    @BeforeEach
    void setUp() {
        tripQueryRepository = Mockito.mock(TripQueryRepository.class);
        service = new TripQueryApplicationService(tripQueryRepository);
    }

    @Test
    void givenRepository_whenGetTripHistory_thenReturnTripHistory() {
        TripHistorySearchCriteria criteria = Mockito.mock(TripHistorySearchCriteria.class);
        TripHistory expectedTripHistory = new TripHistory(List.of());
        Mockito.when(tripQueryRepository.findAllBySearchCriteria(criteria))
                .thenReturn(expectedTripHistory);

        TripHistory result = service.getTripHistory(criteria);

        Assertions.assertEquals(expectedTripHistory, result);
        Mockito.verify(tripQueryRepository, Mockito.times(1)).findAllBySearchCriteria(criteria);
    }
}
