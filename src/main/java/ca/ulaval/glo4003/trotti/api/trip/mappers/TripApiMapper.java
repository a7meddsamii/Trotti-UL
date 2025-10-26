package ca.ulaval.glo4003.trotti.api.trip.mappers;

import ca.ulaval.glo4003.trotti.api.trip.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;

public class TripApiMapper {

    public RidePermitId toRidePermitId(StartTripRequest request) {
        return request.ridePermitId();
    }

    public UnlockCode toUnlockCode(StartTripRequest request) {
        return request.unlockCode();
    }

    public Location toLocation(StartTripRequest request) {
        return request.location();
    }

    public SlotNumber toSlotNumber(StartTripRequest request) {
        return request.slotNumber();
    }

    public Location toLocation(EndTripRequest request) {
        return request.location();
    }

    public SlotNumber toSlotNumber(EndTripRequest request) {
        return request.slotNumber();
    }
}
