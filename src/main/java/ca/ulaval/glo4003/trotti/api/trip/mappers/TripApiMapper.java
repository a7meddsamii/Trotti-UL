package ca.ulaval.glo4003.trotti.api.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import java.time.Clock;

public class TripApiMapper {

    private final Clock clock;

    public TripApiMapper(Clock clock) {
        this.clock = clock;
    }

    // public StartTripDto toStartTripDto(StartTripRequest request) {
    // if (request == null) {
    // throw new InvalidParameterException("StartTripRequest cannot be null");
    // }
    // RidePermitId ridePermitId = RidePermitId.from(request.ridePermitId());
    // UnlockCode unlockCode =
    //
    //
    // return new StartTripDto(ridePermitId, unlockCode, location, slotNumber);
    // }

    private SlotNumber parseSlotNumber(String slotNumberValue) {
        try {
            int number = Integer.parseInt(slotNumberValue);
            return new SlotNumber(number);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Slot number must be an integer value");
        }
    }
}
