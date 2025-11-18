package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.trip.application.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UnlockCodeControllerTest {

    private static final String RIDE_PERMIT_ID = RidePermitId.randomId().toString();

    private UnlockCodeApplicationService unlockCodeApplicationService;
    private UnlockCodeResource unlockCodeController;

    @BeforeEach
    void setup() {
        unlockCodeApplicationService = Mockito.mock(UnlockCodeApplicationService.class);
        AuthenticationService authenticationService = Mockito.mock(AuthenticationService.class);

        unlockCodeController =
                new UnlockCodeController(authenticationService, unlockCodeApplicationService);
    }

    @Test
    void whenRequestUnlockCode_thenServiceGeneratesUnlockCode() {
        unlockCodeController.requestUnlockCode(AUTH_HEADER, RIDE_PERMIT_ID);

        Mockito.verify(unlockCodeApplicationService).generateUnlockCode(Mockito.any(),
                Mockito.any());
    }

    @Test
    void whenRequestUnlockCode_thenReturnsResponseAndHttpCode200() {
        Mockito.doNothing().when(unlockCodeApplicationService).generateUnlockCode(Mockito.any(),
                Mockito.any());

        Response response = unlockCodeController.requestUnlockCode(AUTH_HEADER, RIDE_PERMIT_ID);

        Assertions.assertEquals(200, response.getStatus());
    }
}
