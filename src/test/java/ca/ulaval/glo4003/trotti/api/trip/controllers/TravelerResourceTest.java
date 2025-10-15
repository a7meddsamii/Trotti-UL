package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class TravelerResourceTest {

    private static final String AUTH_HEADER = "Bearer test.jwt.token";

    private RidePermitActivationApplicationService ridePermitService;
    private AuthenticationService authenticationService;
    private TravelerResource resource;

    @BeforeEach
    void setup() {
        ridePermitService = Mockito.mock(RidePermitActivationApplicationService.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        resource = new TravelerResource(ridePermitService, authenticationService);
    }

    @Test
    void givenValidToken_whenGetRidePermits_thenReturns200AndList() {
        Idul idul = Idul.from("rihab");
        List<RidePermitDto> permits =
                List.of(Mockito.mock(RidePermitDto.class), Mockito.mock(RidePermitDto.class));

        Mockito.when(
                authenticationService.authenticate(ArgumentMatchers.any(AuthenticationToken.class)))
                .thenReturn(idul);
        Mockito.when(ridePermitService.getRidePermit(idul)).thenReturn(permits);

        Response response = resource.getRidePermits(AUTH_HEADER);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(permits, response.getEntity());
        Mockito.verify(authenticationService)
                .authenticate(ArgumentMatchers.any(AuthenticationToken.class));
        Mockito.verify(ridePermitService).getRidePermit(idul);
    }

    @Test
    void givenValidTokenAndNoPermits_whenGetRidePermits_thenReturns200AndEmptyList() {
        Idul idul = Idul.from("rihab");
        Mockito.when(
                authenticationService.authenticate(ArgumentMatchers.any(AuthenticationToken.class)))
                .thenReturn(idul);
        Mockito.when(ridePermitService.getRidePermit(idul)).thenReturn(List.of());

        Response response = resource.getRidePermits(AUTH_HEADER);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertTrue(((List<?>) response.getEntity()).isEmpty());
        Mockito.verify(authenticationService)
                .authenticate(ArgumentMatchers.any(AuthenticationToken.class));
        Mockito.verify(ridePermitService).getRidePermit(idul);
    }

    @Test
    void whenAuthenticationFails_thenExceptionIsRaisedAndServiceNotCalled() {
        Mockito.when(
                authenticationService.authenticate(ArgumentMatchers.any(AuthenticationToken.class)))
                .thenThrow(new RuntimeException("auth failed"));

        Assertions.assertThrows(RuntimeException.class, () -> resource.getRidePermits(AUTH_HEADER));

        Mockito.verifyNoInteractions(ridePermitService);
    }

    @Test
    void whenServiceFails_thenExceptionIsRaised() {
        Idul idul = Idul.from("rihab");
        Mockito.when(
                authenticationService.authenticate(ArgumentMatchers.any(AuthenticationToken.class)))
                .thenReturn(idul);
        Mockito.when(ridePermitService.getRidePermit(idul))
                .thenThrow(new RuntimeException("error service"));

        Assertions.assertThrows(RuntimeException.class, () -> resource.getRidePermits(AUTH_HEADER));

        Mockito.verify(authenticationService)
                .authenticate(ArgumentMatchers.any(AuthenticationToken.class));
        Mockito.verify(ridePermitService).getRidePermit(idul);
    }
}
