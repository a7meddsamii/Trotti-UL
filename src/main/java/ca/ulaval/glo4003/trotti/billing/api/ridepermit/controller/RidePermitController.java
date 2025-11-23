package ca.ulaval.glo4003.trotti.billing.api.ridepermit.controller;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.dto.response.RidePermitResponse;
import ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper.RidePermitMapper;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitApplicationService;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class RidePermitController implements RidePermitResource {
	private final RidePermitApplicationService ridePermitApplicationService;
	private final RidePermitMapper ridePermitMapper;
	
	public RidePermitController(
			RidePermitApplicationService ridePermitApplicationService,
			RidePermitMapper ridePermitMapper
	) {
		this.ridePermitApplicationService = ridePermitApplicationService;
		this.ridePermitMapper = ridePermitMapper;
	}
	
	@Override
	public Response getRidePermits(Idul userId) {
		List<RidePermitDto> ridePermitDtoList = ridePermitApplicationService.getRidePermits(userId);
		
		List<RidePermitResponse> ridePermitResponseList = null;
		
		return Response.ok(ridePermitResponseList).build();
	}
	
	@Override
	public Response getRidePermit(Idul userId, String ridePermitId) {
		RidePermitDto ridePermitDto = ridePermitApplicationService.getRidePermit(userId, RidePermitId.from(ridePermitId));
		
		RidePermitResponse ridePermitResponse = null;
		
		return Response.ok(ridePermitResponse).build();
	}
}
