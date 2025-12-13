package ca.ulaval.glo4003.trotti.billing.api.ridePermit.controller;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.controller.RidePermitController;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.dto.response.RidePermitResponse;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper.RidePermitApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RidePermitControllerTest {
    private static final Idul VALID_USER_IDUL = Idul.from("user123");
    private static final String VALID_RIDE_PERMIT_ID_VALUE = UUID.randomUUID().toString();
    private static final int HTTP_STATUS_OK = 200;

    private RidePermitApplicationService ridePermitApplicationService;
    private RidePermitApiMapper ridePermitApiMapper;
    private RidePermitController controller;

    @BeforeEach
    void setUp() {
        ridePermitApplicationService = Mockito.mock(RidePermitApplicationService.class);
        ridePermitApiMapper = Mockito.mock(RidePermitApiMapper.class);

        controller = new RidePermitController(ridePermitApplicationService, ridePermitApiMapper);
    }

    @Test
    void givenValidUserId_whenGetRidePermits_thenReturnsOkResponseWithRidePermitList() {
        RidePermitDto dto1 = Mockito.mock(RidePermitDto.class);
        RidePermitDto dto2 = Mockito.mock(RidePermitDto.class);
        List<RidePermitDto> ridePermitDtos = List.of(dto1, dto2);

        RidePermitResponse response1 = Mockito.mock(RidePermitResponse.class);
        RidePermitResponse response2 = Mockito.mock(RidePermitResponse.class);
        List<RidePermitResponse> ridePermitResponses = List.of(response1, response2);

        Mockito.when(ridePermitApplicationService.getRidePermits(VALID_USER_IDUL))
                .thenReturn(ridePermitDtos);
        Mockito.when(ridePermitApiMapper.toRidePermitResponseList(ridePermitDtos))
                .thenReturn(ridePermitResponses);

        Response result = controller.getRidePermits(VALID_USER_IDUL);

        Assertions.assertEquals(HTTP_STATUS_OK, result.getStatus());
        Assertions.assertEquals(ridePermitResponses, result.getEntity());
        Mockito.verify(ridePermitApplicationService).getRidePermits(VALID_USER_IDUL);
        Mockito.verify(ridePermitApiMapper).toRidePermitResponseList(ridePermitDtos);
    }

    @Test
    void givenValidUserIdAndRidePermitId_whenGetRidePermit_thenReturnsOkResponseWithRidePermit() {
        RidePermitDto ridePermitDto = Mockito.mock(RidePermitDto.class);
        RidePermitResponse ridePermitResponse = Mockito.mock(RidePermitResponse.class);

        Mockito.when(ridePermitApplicationService.getRidePermit(Mockito.eq(VALID_USER_IDUL),
                Mockito.any(RidePermitId.class))).thenReturn(ridePermitDto);
        Mockito.when(ridePermitApiMapper.toRidePermitResponse(ridePermitDto))
                .thenReturn(ridePermitResponse);

        Response result = controller.getRidePermit(VALID_USER_IDUL, VALID_RIDE_PERMIT_ID_VALUE);

        Assertions.assertEquals(HTTP_STATUS_OK, result.getStatus());
        Assertions.assertEquals(ridePermitResponse, result.getEntity());
        Mockito.verify(ridePermitApplicationService).getRidePermit(Mockito.eq(VALID_USER_IDUL),
                Mockito.any(RidePermitId.class));
        Mockito.verify(ridePermitApiMapper).toRidePermitResponse(ridePermitDto);
    }
}
