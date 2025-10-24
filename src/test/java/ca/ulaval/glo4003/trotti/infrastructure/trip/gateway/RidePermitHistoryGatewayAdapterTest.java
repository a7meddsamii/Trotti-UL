package ca.ulaval.glo4003.trotti.infrastructure.trip.gateway;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.domain.order.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.domain.order.values.PassId;
import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitHistoryGatewayAdapterTest {
    private static final Idul AN_IDUL = Idul.from("abcd");
    private PassRepository passRepository;
    private PassId passId;
    private Pass pass;

    private RidePermitHistoryGatewayAdapter ridePermitHistoryGateway;

    @BeforeEach
    void setup() {
        passId = PassId.randomId();
        pass = Mockito.mock(Pass.class);
        passRepository = Mockito.mock(PassRepository.class);
        ridePermitHistoryGateway = new RidePermitHistoryGatewayAdapter(passRepository);
        Mockito.when(pass.getId()).thenReturn(passId);
        Mockito.when(passRepository.findAllByIdul(AN_IDUL)).thenReturn(List.of(pass));
    }

    @Test
    void givenIdul_whenGetFullHistory_thenFetchesPassesFromRepository() {
        ridePermitHistoryGateway.getFullHistory(AN_IDUL);

        Mockito.verify(passRepository).findAllByIdul(AN_IDUL);
    }

    @Test
    void givenIdul_whenGetFullHistory_thenReturnsPassesAsRidePermits() {
        List<RidePermit> answer = ridePermitHistoryGateway.getFullHistory(AN_IDUL);

        Assertions.assertEquals(List.of(pass).size(), answer.size());
    }
}
