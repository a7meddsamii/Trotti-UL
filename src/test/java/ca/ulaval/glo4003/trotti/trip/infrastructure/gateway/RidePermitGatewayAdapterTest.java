package ca.ulaval.glo4003.trotti.trip.infrastructure.gateway;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.repositories.PassRepository;
import ca.ulaval.glo4003.trotti.order.domain.values.PassId;
import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RidePermitGatewayAdapterTest {
    private static final Idul AN_IDUL = Idul.from("abcd");
    private PassRepository passRepository;
    private PassId passId;
    private Pass pass;

    private RidePermitGatewayAdapter ridePermitHistoryGateway;

    @BeforeEach
    void setup() {
        passId = PassId.randomId();
        pass = Mockito.mock(Pass.class);
        passRepository = Mockito.mock(PassRepository.class);
        ridePermitHistoryGateway = new RidePermitGatewayAdapter(passRepository);
        Mockito.when(pass.getId()).thenReturn(passId);
        Mockito.when(passRepository.findAllByIdul(AN_IDUL)).thenReturn(List.of(pass));
    }

    @Test
    void givenIdul_whenFindAllByIdul_thenFetchesPassesFromRepository() {
        ridePermitHistoryGateway.findAllByIdul(AN_IDUL);

        Mockito.verify(passRepository).findAllByIdul(AN_IDUL);
    }

    @Test
    void givenIdul_whenFindAllByIdul_thenReturnsPassesAsRidePermits() {
        List<RidePermit> answer = ridePermitHistoryGateway.findAllByIdul(AN_IDUL);

        Assertions.assertEquals(List.of(pass).size(), answer.size());
    }
}
