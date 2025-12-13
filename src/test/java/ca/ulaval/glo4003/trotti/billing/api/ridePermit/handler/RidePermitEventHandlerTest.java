package ca.ulaval.glo4003.trotti.billing.api.ridePermit.handler;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.handler.RidePermitEventHandler;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper.RidePermitApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.AddTravelTimeDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.OrderPlacedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.RidePermitItemSnapshot;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.TripCompletedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class RidePermitEventHandlerTest {
    private static final Idul VALID_USER_IDUL = Idul.from("user123");

    private RidePermitApiMapper ridePermitApiMapper;
    private RidePermitApplicationService ridePermitApplicationService;
    private RidePermitEventHandler handler;

    @BeforeEach
    void setUp() {
        ridePermitApiMapper = Mockito.mock(RidePermitApiMapper.class);
        ridePermitApplicationService = Mockito.mock(RidePermitApplicationService.class);

        handler = new RidePermitEventHandler(ridePermitApiMapper, ridePermitApplicationService);
    }

    @Test
    void givenOrderPlacedEvent_whenOnOrderPlaced_thenCreatesRidePermits() {
        OrderPlacedEvent event = Mockito.mock(OrderPlacedEvent.class);
        RidePermitItemSnapshot snapshot1 = Mockito.mock(RidePermitItemSnapshot.class);
        RidePermitItemSnapshot snapshot2 = Mockito.mock(RidePermitItemSnapshot.class);
        List<RidePermitItemSnapshot> ridePermitItemSnapshots = List.of(snapshot1, snapshot2);

        CreateRidePermitDto dto1 = Mockito.mock(CreateRidePermitDto.class);
        CreateRidePermitDto dto2 = Mockito.mock(CreateRidePermitDto.class);
        List<CreateRidePermitDto> createDtos = List.of(dto1, dto2);

        Mockito.when(event.getIdul()).thenReturn(VALID_USER_IDUL);
        Mockito.when(event.getRidePermitItems()).thenReturn(ridePermitItemSnapshots);
        Mockito.when(ridePermitApiMapper.toCreateRidePermitDto(ridePermitItemSnapshots))
                .thenReturn(createDtos);

        handler.onOrderPlaced(event);

        Mockito.verify(ridePermitApiMapper).toCreateRidePermitDto(ridePermitItemSnapshots);
        Mockito.verify(ridePermitApplicationService).createRidePermits(VALID_USER_IDUL, createDtos);
    }

    @Test
    void givenTripCompletedEvent_whenOnTripCompleted_thenAddsTravelTime() {
        TripCompletedEvent event = Mockito.mock(TripCompletedEvent.class);
        AddTravelTimeDto addTravelTimeDto = Mockito.mock(AddTravelTimeDto.class);

        Mockito.when(event.getIdul()).thenReturn(VALID_USER_IDUL);
        Mockito.when(ridePermitApiMapper.toAddTimeDto(event))
                .thenReturn(addTravelTimeDto);

        handler.onTripCompleted(event);

        Mockito.verify(ridePermitApiMapper).toAddTimeDto(event);
        Mockito.verify(ridePermitApplicationService).addTravelTime(VALID_USER_IDUL, addTravelTimeDto);
    }
}
