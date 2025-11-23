package ca.ulaval.glo4003.trotti.billing.api.ridepermit.handler;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;

public class RidePermitEventHandler {
	private RidePermitApplicationService ridePermitApplicationService;
	
	public RidePermitEventHandler(RidePermitApplicationService ridePermitApplicationService) {
		this.ridePermitApplicationService = ridePermitApplicationService;
	}
	
	public void onAccountCreated(AccountCreatedEvent accountCreatedEvent) {
		ridePermitApplicationService.createWelcomeRidePermit(accountCreatedEvent.idul());
	}
}
