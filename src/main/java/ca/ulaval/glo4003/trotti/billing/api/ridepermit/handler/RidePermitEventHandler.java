package ca.ulaval.glo4003.trotti.billing.api.ridepermit.handler;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper.RidePermitApiMapper;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.AddTravelTimeDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.OrderPlacedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.TripCompletedEvent;

import java.util.List;

public class RidePermitEventHandler {

    private final RidePermitApiMapper ridePermitApiMapper;
    private final RidePermitApplicationService ridePermitApplicationService;
	
	public RidePermitEventHandler(
			RidePermitApiMapper ridePermitApiMapper,
			RidePermitApplicationService ridePermitApplicationService) {
        this.ridePermitApiMapper = ridePermitApiMapper;
        this.ridePermitApplicationService = ridePermitApplicationService;
	}

    public void onOrderPlaced(OrderPlacedEvent orderPlacedEvent) {
        List<CreateRidePermitDto> ridePermitToCreate =
            ridePermitApiMapper.toCreateRidePermitDto(orderPlacedEvent.getRidePermitItems());

    	ridePermitApplicationService.createRidePermits(orderPlacedEvent.getIdul(), ridePermitToCreate);
    }
	
	public void onTripCompleted(TripCompletedEvent tripCompletedEvent) {
		AddTravelTimeDto addTravelTimeDto = ridePermitApiMapper.toAddTimeDto(tripCompletedEvent);
		Idul riderId = tripCompletedEvent.getIdul();
		ridePermitApplicationService.addTravelTime(riderId, addTravelTimeDto);
	}
}
