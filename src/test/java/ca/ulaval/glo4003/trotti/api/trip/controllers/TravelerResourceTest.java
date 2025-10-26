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
import org.mockito.Mockito;

class TravelerResourceTest {

    private static final String AUTH_HEADER = "Bearer test.jwt.token";
    private static final String VALID_IDUL = "Equipe10";

    private RidePermitActivationApplicationService ridePermitService;
    private AuthenticationService authenticationService;
    private TravelerResource resource;
    private Idul idul;
    private AuthenticationToken token;

    @BeforeEach
    void setup() {
        ridePermitService = Mockito.mock(RidePermitActivationApplicationService.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        resource = new TravelerResource(ridePermitService, authenticationService);
        idul = Idul.from(VALID_IDUL);
        token = AuthenticationToken.from(AUTH_HEADER);
    }

    private void stubValidAuthentication() {
        Mockito.when(authenticationService.authenticate(token)).thenReturn(idul);
    }

    private void stubInvalidAuthentication(RuntimeException toThrow) {
        Mockito.when(authenticationService.authenticate(token)).thenThrow(toThrow);
    }

    @Test
    void givenValidToken_whenGetRidePermits_thenReturns200() {
        stubValidAuthentication();
        List<RidePermitDto> permits =
                List.of(Mockito.mock(RidePermitDto.class), Mockito.mock(RidePermitDto.class));
        Mockito.when(ridePermitService.getRidePermit(idul)).thenReturn(permits);

        Response response = resource.getRidePermits(AUTH_HEADER);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(permits, response.getEntity());
        Mockito.verify(authenticationService).authenticate(token);
        Mockito.verify(ridePermitService).getRidePermit(idul);
    }

    @Test
    void givenValidTokenAndNoPermits_whenGetRidePermits_thenReturns200AndEmptyList() {
        stubValidAuthentication();
        Mockito.when(ridePermitService.getRidePermit(idul)).thenReturn(List.of());

        Response response = resource.getRidePermits(AUTH_HEADER);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertTrue(((List<?>) response.getEntity()).isEmpty());
        Mockito.verify(authenticationService).authenticate(token);
        Mockito.verify(ridePermitService).getRidePermit(idul);
    }

    @Test
    void givenInvalidToken_whenGetRidePermits_thenThrowException() {
        stubInvalidAuthentication(new RuntimeException("auth failed"));

        Assertions.assertThrows(RuntimeException.class, () -> resource.getRidePermits(AUTH_HEADER));
        Mockito.verify(authenticationService).authenticate(token);
        Mockito.verifyNoInteractions(ridePermitService);
    }

    @Test
    void givenRidePermitServiceException_whenGetPermits_thenThrowException() {
        stubValidAuthentication();
        Mockito.when(ridePermitService.getRidePermit(idul))
                .thenThrow(new RuntimeException("Service Exception"));

        Assertions.assertThrows(RuntimeException.class, () -> resource.getRidePermits(AUTH_HEADER));
        Mockito.verify(authenticationService).authenticate(token);
        Mockito.verify(ridePermitService).getRidePermit(idul);
    }
}
