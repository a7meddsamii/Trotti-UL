package ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.dto.response.RidePermitResponse;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;

import java.util.List;

public class RidePermitMapper {
	
	public List<RidePermitResponse> toRidePermitResponseList(List<RidePermitDto> ridePermitDtos) {
		return ridePermitDtos.stream().map(this::toRidePermitResponse).toList();
	}
	
	public RidePermitResponse toRidePermitResponse(RidePermitDto ridePermitDto) {
		return new RidePermitResponse(
			ridePermitDto.ridePermitId().toString(),
			ridePermitDto.riderId().toString(),
			ridePermitDto.session().toString(),
			ridePermitDto.maximumTravelingTimePerDay().toString(),
			ridePermitDto.permitState().toString(),
			ridePermitDto.balance().toString()
		);
	}
}
