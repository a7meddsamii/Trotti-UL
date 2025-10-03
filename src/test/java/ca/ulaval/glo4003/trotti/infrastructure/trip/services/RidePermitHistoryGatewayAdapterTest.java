package ca.ulaval.glo4003.trotti.infrastructure.trip.services;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repository.PassRepository;
import ca.ulaval.glo4003.trotti.domain.trip.RidePermit;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitHistoryGatewayAdapterTest {
    private static final Idul AN_IDUL = Idul.from("abcd");
    private RidePermitHistoryGatewayAdapter ridePermitHistoryGateway;
    private PassRepository passRepository;

    @BeforeEach
    void setup() {
        passRepository = Mockito.mock(PassRepository.class);
        ridePermitHistoryGateway = new RidePermitHistoryGatewayAdapter(passRepository);
    }

    @Test
    void givenIdul_whenGetFullHistory_thenFetchesPassesFromRepository() {
        Pass pass = Mockito.mock(Pass.class);
        Mockito.when(passRepository.findAllByIdul(AN_IDUL)).thenReturn(List.of(pass));

        ridePermitHistoryGateway.getFullHistory(AN_IDUL);

        Mockito.verify(passRepository).findAllByIdul(AN_IDUL);
    }

    @Test
    void givenIdul_whenGetFullHistory_thenReturnsPassesAsRidePermits() {
        Pass pass = Mockito.mock(Pass.class);
        Mockito.when(passRepository.findAllByIdul(AN_IDUL)).thenReturn(List.of(pass));

        List<RidePermit> answer = ridePermitHistoryGateway.getFullHistory(AN_IDUL);

        Assertions.assertEquals(List.of(pass).size(), answer.size());
    }
}
