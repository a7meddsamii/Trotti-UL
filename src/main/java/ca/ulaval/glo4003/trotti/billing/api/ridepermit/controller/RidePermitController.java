package ca.ulaval.glo4003.trotti.billing.api.ridepermit.controller;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.ws.rs.core.Response;

public class RidePermitController {
	private final RidePermitApplicationService ridePermitApplicationService;
	
	public RidePermitController(RidePermitApplicationService ridePermitApplicationService) {
		this.ridePermitApplicationService = ridePermitApplicationService;
	}
	
	public Response getRidePermits(Idul userId) {
//		List<
		return Response.ok().build();
	}
}
