package ca.ulaval.glo4003.trotti.trip.api.mappers;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.responses.TripHistoryResponse;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

class TripApiMapperTest {

    private static final String VALID_IDUL_VALUE = "Equipe10";
    private static final String VALID_LOCATION = "VACHON";
    private static final String VALID_SLOT_NUMBER = "3";
    private static final String INVALID_SLOT_NUMBER = "not-a-number";
    private static final String UNLOCK_CODE_VALUE = "23123";

    private Idul travelerIdul;
    private TripApiMapper mapper;

    @BeforeEach
    void setUp() {
        travelerIdul = Idul.from(VALID_IDUL_VALUE);
        mapper = new TripApiMapper();
    }

    @Test
    void givenValidStartTripRequest_whenToStartTripDto_thenMapsCorrectly() {
        RidePermitId expectedRidePermitId = RidePermitId.randomId();
        StartTripRequest request = new StartTripRequest(expectedRidePermitId.toString(),
                UNLOCK_CODE_VALUE, VALID_LOCATION, VALID_SLOT_NUMBER);

        StartTripDto dto = mapper.toStartTripDto(travelerIdul, request);

        Assertions.assertEquals(travelerIdul, dto.idul());
        Assertions.assertEquals(expectedRidePermitId, dto.ridePermitId());
        Assertions.assertEquals(Location.of(VALID_LOCATION), dto.location());
        Assertions.assertEquals(new SlotNumber(Integer.parseInt(VALID_SLOT_NUMBER)),
                dto.slotNumber());
        Assertions.assertNotNull(dto.unlockCode());
    }

    @Test
    void givenValidEndTripRequest_whenToEndTripDto_thenMapsCorrectly() {
        EndTripRequest request = createValidEndTripRequest();

        EndTripDto dto = mapper.toEndTripDto(travelerIdul, request);

        Assertions.assertEquals(travelerIdul, dto.idul());
        Assertions.assertEquals(Location.of(VALID_LOCATION), dto.location());
        Assertions.assertEquals(new SlotNumber(Integer.parseInt(VALID_SLOT_NUMBER)),
                dto.slotNumber());
    }

    @Test
    void givenNullStartTripRequest_whenToStartTripDto_thenThrowsInvalidParameterException() {
        Executable action = () -> mapper.toStartTripDto(travelerIdul, null);

        Assertions.assertThrows(InvalidParameterException.class, action);
    }

    @Test
    void givenNullEndTripRequest_whenToEndTripDto_thenThrowsInvalidParameterException() {
        Executable action = () -> mapper.toEndTripDto(travelerIdul, null);

        Assertions.assertThrows(InvalidParameterException.class, action);
    }

    @Test
    void givenInvalidSlotNumberInEndTripRequest_whenToEndTripDto_thenThrowsInvalidParameterException() {
        EndTripRequest request = createEndTripRequestWithInvalidSlotNumber();

        Executable action = () -> mapper.toEndTripDto(travelerIdul, request);

        Assertions.assertThrows(InvalidParameterException.class, action);
    }

    @Test
    void givenTwoTrips_whenMapping_thenCorrectAggregateValuesAreReturned() {
        CompletedTrip t1 = trip( "Vachon", "Desjardins", 10);
        CompletedTrip t2 = trip( "Vachon", "Pouliot", 20);

        TripHistory history = new TripHistory(List.of(t1, t2));

        TripHistoryResponse response = mapper.toTripHistoryResponse(history);

        Assertions.assertEquals(Duration.ofMinutes(30), response.totalDuration());
        Assertions.assertEquals(2, response.numberOfTrips());
        Assertions.assertEquals(Duration.ofMinutes(15), response.averageTripDuration());

        Assertions.assertEquals("Vachon", response.favoriteStartStation());

        Assertions.assertTrue(
                List.of("Desjardins", "Pouliot").contains(response.favoriteEndStation())
        );

        Assertions.assertEquals(2, response.trips().size());
    }

    @Test
    void givenEmptyTripHistory_whenMapping_thenReturnsDefaultValues() {
        TripHistory history = new TripHistory(List.of());

        TripHistoryResponse response = mapper.toTripHistoryResponse(history);

        Assertions.assertEquals(Duration.ZERO, response.totalDuration());
        Assertions.assertEquals(0, response.numberOfTrips());
        Assertions.assertEquals(Duration.ZERO, response.averageTripDuration());
        Assertions.assertEquals("N/A", response.favoriteStartStation());
        Assertions.assertEquals("N/A", response.favoriteEndStation());
        Assertions.assertTrue(response.trips().isEmpty());
    }

    @Test
    void givenRepeatedEndLocations_whenMapping_thenCorrectFavoriteEndReturned() {
        CompletedTrip t1 = trip("Vachon", "Desjardins", 5);
        CompletedTrip t2 = trip("Pouliot", "Desjardins", 10);

        TripHistory history = new TripHistory(List.of(t1, t2));

        TripHistoryResponse response = mapper.toTripHistoryResponse(history);

        Assertions.assertEquals("Desjardins", response.favoriteEndStation());
    }

    private EndTripRequest createValidEndTripRequest() {
        return new EndTripRequest(VALID_LOCATION, VALID_SLOT_NUMBER);
    }

    private EndTripRequest createEndTripRequestWithInvalidSlotNumber() {
        return new EndTripRequest(VALID_LOCATION, INVALID_SLOT_NUMBER);
    }

    private CompletedTrip trip(
                               String startBuilding,
                               String endBuilding,
                               int minutes) {

        TripId tripId = TripId.randomId();
        Idul idul = Idul.from("UL123");
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 12, 0);
        LocalDateTime end = start.plusMinutes(minutes);

        return new CompletedTrip(
                tripId,
                idul,
                start,
                Location.of(startBuilding, "S1"),
                end,
                Location.of(endBuilding, "E1")
        );
    }
}
