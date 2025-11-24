package ca.ulaval.glo4003.trotti.trip.api.controllers;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.trip.application.TripApplicationService;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TripControllerTest {

    private static final String IDUL_VALUE = "Equipe10";
    private static final Idul TRAVELER_IDUL = Idul.from(IDUL_VALUE);
    private static final String UNLOCK_CODE = "23123";
    private static final String LOCATION = "VACHON";
    private static final String SLOT_NUMBER = "3";
    private static final String RIDE_PERMIT_ID = "rideId";

    private TripApplicationService tripApplicationService;
    private TripApiMapper tripApiMapper;
    private StartTripDto startTripDto;
    private EndTripDto endTripDto;

    private TripController resource;

    @BeforeEach
    void setUp() {
        tripApplicationService = Mockito.mock(TripApplicationService.class);
        tripApiMapper = Mockito.mock(TripApiMapper.class);
        startTripDto = Mockito.mock(StartTripDto.class);
        endTripDto = Mockito.mock(EndTripDto.class);

        resource = new TripController(tripApplicationService, tripApiMapper);

        Mockito.when(tripApiMapper.toStartTripDto(TRAVELER_IDUL, startTripRequest()))
                .thenReturn(startTripDto);
        Mockito.when(tripApiMapper.toEndTripDto(TRAVELER_IDUL, endTripRequest()))
                .thenReturn(endTripDto);
    }

    @Test
    void givenStartTripRequest_whenStartTrip_thenReturnsOkResponse() {
        Response response = resource.startTrip(TRAVELER_IDUL, startTripRequest());

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void givenEndTripRequest_whenEndTrip_thenReturnsOkResponse() {
        Response response = resource.endTrip(TRAVELER_IDUL, endTripRequest());

        Assertions.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    private StartTripRequest startTripRequest() {
        return new StartTripRequest(RIDE_PERMIT_ID, UNLOCK_CODE, LOCATION, SLOT_NUMBER);
    }

    private EndTripRequest endTripRequest() {
        return new EndTripRequest(LOCATION, RIDE_PERMIT_ID);
    }
}
