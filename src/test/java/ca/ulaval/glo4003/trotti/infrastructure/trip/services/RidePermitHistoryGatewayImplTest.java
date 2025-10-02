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


class RidePermitHistoryGatewayImplTest {

    private static final String A_IDUL = "abcd";

    private RidePermitHistoryGatewayImpl ridePermitHistoryGateway;
    private PassRepository passRepository;

    @BeforeEach
    void setup() {
        passRepository = Mockito.mock(PassRepository.class);
        ridePermitHistoryGateway = new RidePermitHistoryGatewayImpl(passRepository);
    }

    @Test
    void givenIdul_whenGetFullHistory_thenVerifyGetAllPassesIsCalled() {
        Idul idul = Idul.from(A_IDUL);
        Pass pass = Mockito.mock(Pass.class);
        Mockito.when(passRepository.getAllPasses(idul)).thenReturn(List.of(pass));

        ridePermitHistoryGateway.getFullHistory(idul);

        Mockito.verify(passRepository).getAllPasses(idul);
    }

    @Test
    void givenIdul_whenGetFullHistory_thenReturnsPassesAsRidePermits() {
        Idul idul = Idul.from(A_IDUL);
        Pass pass = Mockito.mock(Pass.class);
        Mockito.when(passRepository.getAllPasses(idul)).thenReturn(List.of(pass));

        List<RidePermit> answer = ridePermitHistoryGateway.getFullHistory(idul);

        Assertions.assertEquals(List.of(pass).size(), answer.size());
    }

}