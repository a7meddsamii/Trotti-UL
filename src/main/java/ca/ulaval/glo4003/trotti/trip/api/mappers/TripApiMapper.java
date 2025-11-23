package ca.ulaval.glo4003.trotti.trip.api.mappers;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.EndTripRequest;
import ca.ulaval.glo4003.trotti.trip.api.dto.requests.StartTripRequest;
import ca.ulaval.glo4003.trotti.trip.application.dto.EndTripDto;
import ca.ulaval.glo4003.trotti.trip.application.dto.StartTripDto;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;

public class TripApiMapper {

    public StartTripDto toStartTripDto(Idul idul, StartTripRequest request) {
        if (request == null) {
            throw new InvalidParameterException("StartTripRequest cannot be null");
        }
        RidePermitId ridePermitId = RidePermitId.from(request.ridePermitId());
        Location location = Location.of(request.location());
        SlotNumber slotNumber = parseSlotNumber(request.slotNumber());

        return new StartTripDto(idul, ridePermitId, String.valueOf(request.unlockCode()), location,
                slotNumber);
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
