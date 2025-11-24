package ca.ulaval.glo4003.trotti.billing.api.ridepermit.mapper;

import ca.ulaval.glo4003.trotti.billing.api.ridepermit.dto.response.RidePermitResponse;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.AddTravelTimeDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.CreateRidePermitDto;
import ca.ulaval.glo4003.trotti.billing.application.ridepermit.dto.RidePermitDto;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.events.billing.order.RidePermitItemSnapshot;
import ca.ulaval.glo4003.trotti.commons.domain.events.trip.TripCompletedEvent;
import java.time.Duration;
import java.util.List;

public class RidePermitApiMapper {

    public List<CreateRidePermitDto> toCreateRidePermitDto(
            List<RidePermitItemSnapshot> ridePermitItemSnapshots) {
        return ridePermitItemSnapshots.stream()
                .map(ridePermitItemSnapshot -> new CreateRidePermitDto(
                        ridePermitItemSnapshot.session(),
                        ridePermitItemSnapshot.maxDailyTravelTime(),
                        ridePermitItemSnapshot.billingFrequency()))
                .toList();
    }

    public List<RidePermitResponse> toRidePermitResponseList(List<RidePermitDto> ridePermitDtos) {
        return ridePermitDtos.stream().map(this::toRidePermitResponse).toList();
    }

    public RidePermitResponse toRidePermitResponse(RidePermitDto ridePermitDto) {
        return new RidePermitResponse(ridePermitDto.ridePermitId().toString(),
                ridePermitDto.riderId().toString(), ridePermitDto.session().toString(),
                ridePermitDto.maximumTravelingTimePerDay().toString(),
                ridePermitDto.permitState().toString(), ridePermitDto.balance().toString());
    }

    public AddTravelTimeDto toAddTimeDto(TripCompletedEvent tripCompletedEvent) {
        RidePermitId ridePermitId = RidePermitId.from(tripCompletedEvent.getRidePermitId());
        Duration travelTime = Duration.between(tripCompletedEvent.getStartTime(),
                tripCompletedEvent.getEndTime());

        return new AddTravelTimeDto(ridePermitId, tripCompletedEvent.getStartTime(), travelTime);
    }
}
