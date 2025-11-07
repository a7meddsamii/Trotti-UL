package ca.ulaval.glo4003.trotti.trip.api.mappers;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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

    private EndTripRequest createValidEndTripRequest() {
        return new EndTripRequest(VALID_LOCATION, VALID_SLOT_NUMBER);
    }

    private EndTripRequest createEndTripRequestWithInvalidSlotNumber() {
        return new EndTripRequest(VALID_LOCATION, INVALID_SLOT_NUMBER);
    }
}
