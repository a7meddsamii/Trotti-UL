package ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.dto.response.RidePermitResponse;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.RidePermitItemSnapshot;

import java.util.List;

public class RidePermitMapper {

    public List<CreateRidePermitDto> toCreateRidePermitDto(List<RidePermitItemSnapshot> ridePermitItemSnapshots) {
        return ridePermitItemSnapshots.stream().map(ridePermitItemSnapshot -> new CreateRidePermitDto(
                ridePermitItemSnapshot.session(),
                ridePermitItemSnapshot.maxDailyTravelTime(),
                ridePermitItemSnapshot.billingFrequency()
        )).toList();
    }
	
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
