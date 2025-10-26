package ca.ulaval.glo4003.trotti.api.trip.controllers;

import ca.ulaval.glo4003.trotti.application.trip.TripApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class TripResourceTest {

    private static final String AUTH_HEADER = "Bearer test.jwt.token";
    private static final Idul TRAVELER_IDUL = Idul.from("Equipe10");
    private static final Location LOCATION = Location.of("VACHON", "Entrée Vachon #1");
    private static final SlotNumber SLOT_NUMBER = new SlotNumber(3);
    private static final RidePermitId RIDE_PERMIT_ID = RidePermitId.randomId();

    private TripApplicationService tripApplicationService;
    private AuthenticationService authenticationService;

    private TripResource resource;

    @BeforeEach
    void setUp() {
        tripApplicationService = Mockito.mock(TripApplicationService.class);
        authenticationService = Mockito.mock(AuthenticationService.class);
        resource = new TripResource(tripApplicationService, authenticationService);

        Mockito.when(
                authenticationService.authenticate(ArgumentMatchers.any(AuthenticationToken.class)))
                .thenReturn(TRAVELER_IDUL);
    }

    // @Test
    // void givenValidStartRequest_whenStartTrip_thenReturnsOkAndDelegatesToService() {
    // UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
    // StartTripRequest request =
    // new StartTripRequest(RIDE_PERMIT_ID, unlockCode, LOCATION, SLOT_NUMBER);
    //
    // Response response = resource.startTrip(AUTH_HEADER, request);
    //
    // Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    // Mockito.verify(authenticationService)
    // .authenticate(ArgumentMatchers.any(AuthenticationToken.class));
    // Mockito.verify(tripApplicationService).startTrip(TRAVELER_IDUL, RIDE_PERMIT_ID, unlockCode,
    // LOCATION, SLOT_NUMBER);
    // }
    //
    // @Test
    // void givenInvalidUnlockCode_whenStartTrip_thenThrowUnlockCodeException() {
    // UnlockCode unlockCode = Mockito.mock(UnlockCode.class);
    // StartTripRequest request =
    // new StartTripRequest(RIDE_PERMIT_ID, unlockCode, LOCATION, SLOT_NUMBER);
    // Mockito.doThrow(new UnlockCodeException("Invalid or expired unlock code"))
    // .when(tripApplicationService)
    // .startTrip(TRAVELER_IDUL, RIDE_PERMIT_ID, unlockCode, LOCATION, SLOT_NUMBER);
    //
    // Executable action = () -> resource.startTrip(AUTH_HEADER, request);
    //
    // Assertions.assertThrows(UnlockCodeException.class, action);
    // Mockito.verify(authenticationService)
    // .authenticate(ArgumentMatchers.any(AuthenticationToken.class));
    // Mockito.verify(tripApplicationService).startTrip(TRAVELER_IDUL, RIDE_PERMIT_ID, unlockCode,
    // LOCATION, SLOT_NUMBER);
    // }
    //
    // @Test
    // void givenInvalidToken_whenStartTrip_thenThrowExceptionAndServiceNotCalled() {
    // StartTripRequest request = new StartTripRequest(RIDE_PERMIT_ID,
    // Mockito.mock(UnlockCode.class), LOCATION, SLOT_NUMBER);
    // Mockito.when(
    // authenticationService.authenticate(ArgumentMatchers.any(AuthenticationToken.class)))
    // .thenThrow(new RuntimeException("auth failed"));
    //
    // Executable action = () -> resource.startTrip(AUTH_HEADER, request);
    //
    // Assertions.assertThrows(RuntimeException.class, action);
    // Mockito.verify(tripApplicationService, Mockito.never()).startTrip(ArgumentMatchers.any(),
    // ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(),
    // ArgumentMatchers.any());
    // }
    //
    // @Test
    // void givenValidEndRequest_whenEndTrip_thenReturnsNoContentAndDelegatesToService() {
    // EndTripRequest request = new EndTripRequest(LOCATION, SLOT_NUMBER);
    //
    // Response response = resource.endTrip(AUTH_HEADER, request);
    //
    // Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    // Mockito.verify(authenticationService)
    // .authenticate(ArgumentMatchers.any(AuthenticationToken.class));
    // Mockito.verify(tripApplicationService).endTrip(TRAVELER_IDUL, SLOT_NUMBER, LOCATION);
    // }
    //
    // @Test
    // void givenInvalidToken_whenEndTrip_thenThrowExceptionAndServiceNotCalled() {
    // EndTripRequest request = new EndTripRequest(LOCATION, SLOT_NUMBER);
    // Mockito.when(
    // authenticationService.authenticate(ArgumentMatchers.any(AuthenticationToken.class)))
    // .thenThrow(new RuntimeException("auth failed"));
    //
    // Executable action = () -> resource.endTrip(AUTH_HEADER, request);
    //
    // Assertions.assertThrows(RuntimeException.class, action);
    // Mockito.verify(tripApplicationService, Mockito.never()).endTrip(ArgumentMatchers.any(),
    // ArgumentMatchers.any(), ArgumentMatchers.any());
    // }
}
