package ca.ulaval.glo4003.trotti.billing.api.ridepermit.handler;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper.RidePermitMapper;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountCreatedEvent;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.OrderPlacedEvent;

import java.util.List;

public class RidePermitEventHandler {

    private final RidePermitMapper ridePermitMapper;
    private final RidePermitApplicationService ridePermitApplicationService;
	
	public RidePermitEventHandler(RidePermitMapper ridePermitMapper,
                                  RidePermitApplicationService ridePermitApplicationService) {
        this.ridePermitMapper = ridePermitMapper;
        this.ridePermitApplicationService = ridePermitApplicationService;
	}
	
	public void onAccountCreated(AccountCreatedEvent accountCreatedEvent) {
//		ridePermitApplicationService.createWelcomeRidePermit(accountCreatedEvent.idul());
	}

    public void onOrderPlaced(OrderPlacedEvent orderPlacedEvent) {
        List<CreateRidePermitDto> ridePermitToCreate =
            ridePermitMapper.toCreateRidePermitDto(orderPlacedEvent.getRidePermitItems());

    	ridePermitApplicationService.createRidePermits(orderPlacedEvent.getIdul(), ridePermitToCreate);
    }
}
