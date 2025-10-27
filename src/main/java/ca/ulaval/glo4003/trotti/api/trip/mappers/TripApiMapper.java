package ca.ulaval.glo4003.trotti.api.trip.mappers;

import ca.ulaval.glo4003.trotti.api.trip.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.api.trip.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.application.trip.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.application.trip.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;

public class TripApiMapper {

    public StartTripDto toStartTripDto(Idul idul, StartTripRequest request) {
        if (request == null) {
            throw new InvalidParameterException("StartTripRequest cannot be null");
        }
        RidePermitId ridePermitId = RidePermitId.from(request.ridePermitId());
        UnlockCode unlockCode = UnlockCode.generateFromTravelerId(idul);
        Location location = Location.of(request.location());
        SlotNumber slotNumber = parseSlotNumber(request.slotNumber());

        return new StartTripDto(idul, ridePermitId, unlockCode, location, slotNumber);
    }

    public EndTripDto toEndTripDto(Idul idul, EndTripRequest request) {
        if (request == null) {
            throw new InvalidParameterException("EndTripRequest cannot be null");
        }

        Location location = Location.of(request.location());
        SlotNumber slotNumber = parseSlotNumber(request.slotNumber());

        return new EndTripDto(idul, location, slotNumber);
    }

    private SlotNumber parseSlotNumber(String slotNumberValue) {
        try {
            int number = Integer.parseInt(slotNumberValue);
            return new SlotNumber(number);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Slot number must be an integer value");
        }
    }
}
