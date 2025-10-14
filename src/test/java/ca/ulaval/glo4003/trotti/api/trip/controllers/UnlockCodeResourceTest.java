package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.api.trip.mappers.UnlockCodeApiMapper;
import ca.ulaval.glo4003.trotti.application.trip.UnlockCodeApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.dto.UnlockCodeDto;
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

    private UnlockCodeApiMapper unlockCodeApiMapper;
    private UnlockCodeApplicationService unlockCodeApplicationService;
    private AuthenticationService authenticationService;
    private UnlockCodeDto unlockCodeDto;
    private UnlockCodeResource unlockCodeResource;

    @BeforeEach
    void setup() {
        unlockCodeApiMapper = Mockito.mock(UnlockCodeApiMapper.class);
        unlockCodeApplicationService = Mockito.mock(UnlockCodeApplicationService.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        unlockCodeDto = Mockito.mock(UnlockCodeDto.class);

        unlockCodeResource = new UnlockCodeResource(authenticationService, unlockCodeApplicationService, unlockCodeApiMapper);
    }

    @Test
    void whenRequestUnlockCode_thenServiceGeneratesUnlockCode() {
        unlockCodeResource.requestUnlockCode(AUTH_HEADER, RIDE_PERMIT_ID);

        Mockito.verify(unlockCodeApplicationService).generateUnlockCode(Mockito.any(), Mockito.any());
    }

    @Test
    void whenRequestUnlockCode_thenReturnsResponseAndHttpCode200() {
        Mockito.when(unlockCodeApplicationService.generateUnlockCode(Mockito.any(), Mockito.any())).thenReturn(unlockCodeDto);

        Response response = unlockCodeResource.requestUnlockCode(AUTH_HEADER, RIDE_PERMIT_ID);

        Assertions.assertEquals(200, response.getStatus());
        Mockito.verify(unlockCodeApiMapper).toResponse(unlockCodeDto);
    }
}