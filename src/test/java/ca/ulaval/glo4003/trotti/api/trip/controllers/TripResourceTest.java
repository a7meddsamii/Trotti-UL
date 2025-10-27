package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.api.trip.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.mappers.TripApiMapper;
import ca.ulaval.glo4003.trotti.application.trip.TripApplicationService;
import ca.ulaval.glo4003.trotti.application.trip.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.application.trip.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class TripResourceTest {

    private static final String AUTH_HEADER = "bdhhd22uemenehjebnee32j3un33kj3n3n3n";
    private static final String IDUL_VALUE = "Equipe10";
    private static final Idul TRAVELER_IDUL = Idul.from(IDUL_VALUE);
    private static final String UNLOCK_CODE = "23123";
    private static final String LOCATION = "VACHON";
    private static final String SLOT_NUMBER = "3";
    private static final String RIDE_PERMIT_ID = "rideId";

    private TripApplicationService tripApplicationService;
    private AuthenticationService authenticationService;
    private TripApiMapper tripApiMapper;
    private StartTripDto startTripDto;
    private EndTripDto endTripDto;

    private TripResource resource;

    @BeforeEach
    void setUp() {
        tripApplicationService = Mockito.mock(TripApplicationService.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        tripApiMapper = Mockito.mock(TripApiMapper.class);
        startTripDto = Mockito.mock(StartTripDto.class);
        endTripDto = Mockito.mock(EndTripDto.class);

        resource = new TripResource(tripApplicationService, authenticationService, tripApiMapper);

        Mockito.when(
                authenticationService.authenticate(ArgumentMatchers.any(AuthenticationToken.class)))
                .thenReturn(TRAVELER_IDUL);
        Mockito.when(tripApiMapper.toStartTripDto(TRAVELER_IDUL, startTripRequest()))
                .thenReturn(startTripDto);
        Mockito.when(tripApiMapper.toEndTripDto(TRAVELER_IDUL, endTripRequest()))
                .thenReturn(endTripDto);
    }

    @Test
    void givenValidTokenAndStartTripRequest_whenStartTrip_thenReturnsOkResponse() {
        Response response = resource.startTrip(AUTH_HEADER, startTripRequest());

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void givenValidTokenAndStartTripRequest_whenStartTrip_thenMapsRequestToDto() {
        resource.startTrip(AUTH_HEADER, startTripRequest());

        Mockito.verify(tripApiMapper).toStartTripDto(TRAVELER_IDUL, startTripRequest());
    }

    @Test
    void givenValidTokenAndStartTripRequest_whenStartTrip_thenCallsApplicationService() {
        resource.startTrip(AUTH_HEADER, startTripRequest());

        Mockito.verify(tripApplicationService).startTrip(startTripDto);
    }

    @Test
    void givenValidTokenAndStartTripRequest_whenStartTrip_thenAuthenticatesUser() {
        resource.startTrip(AUTH_HEADER, startTripRequest());

        Mockito.verify(authenticationService).authenticate(ArgumentMatchers.any());
    }

    @Test
    void givenValidTokenAndEndTripTripRequest_whenEndTrip_thenAuthenticatesUser() {
        resource.endTrip(AUTH_HEADER, endTripRequest());

        Mockito.verify(authenticationService).authenticate(ArgumentMatchers.any());
    }

    @Test
    void givenValidTokenAndEndTripRequest_whenEndTrip_thenMapsRequestToDto() {
        resource.endTrip(AUTH_HEADER, endTripRequest());

        Mockito.verify(tripApiMapper).toEndTripDto(TRAVELER_IDUL, endTripRequest());
    }

    @Test
    void givenValidTokenAndEndTripRequest_whenEndTrip_thenReturnsOkResponse() {
        Response response = resource.endTrip(AUTH_HEADER, endTripRequest());

        Assertions.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    private StartTripRequest startTripRequest() {
        return new StartTripRequest(RIDE_PERMIT_ID, UNLOCK_CODE, LOCATION, SLOT_NUMBER);
    }

    private EndTripRequest endTripRequest() {
        return new EndTripRequest(LOCATION, RIDE_PERMIT_ID);
    }
}
