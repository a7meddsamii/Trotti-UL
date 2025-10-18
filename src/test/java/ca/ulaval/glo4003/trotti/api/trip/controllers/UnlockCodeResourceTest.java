package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.application.trip.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UnlockCodeResourceTest {

    private static final String AUTH_HEADER = "Bearer test.jwt.token";
    private static final String RIDE_PERMIT_ID = Id.randomId().toString();

    private UnlockCodeApplicationService unlockCodeApplicationService;
    private AuthenticationService authenticationService;
    private UnlockCodeResource unlockCodeResource;

    @BeforeEach
    void setup() {
        unlockCodeApplicationService = Mockito.mock(UnlockCodeApplicationService.class);
        authenticationService = Mockito.mock(AuthenticationService.class);

        unlockCodeResource =
                new UnlockCodeResource(authenticationService, unlockCodeApplicationService);
    }

    @Test
    void whenRequestUnlockCode_thenServiceGeneratesUnlockCode() {
        unlockCodeResource.requestUnlockCode(AUTH_HEADER, RIDE_PERMIT_ID);

        Mockito.verify(unlockCodeApplicationService).generateUnlockCode(Mockito.any(),
                Mockito.any());
    }

    @Test
    void whenRequestUnlockCode_thenReturnsResponseAndHttpCode200() {
        Mockito.doNothing().when(unlockCodeApplicationService).generateUnlockCode(Mockito.any(),
                Mockito.any());

        Response response = unlockCodeResource.requestUnlockCode(AUTH_HEADER, RIDE_PERMIT_ID);

        Assertions.assertEquals(200, response.getStatus());
    }
}
