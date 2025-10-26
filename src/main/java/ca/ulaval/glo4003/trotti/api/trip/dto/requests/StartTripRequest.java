package ca.ulaval.glo4003.trotti.api.trip.dto.requests;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;

public record StartTripRequest (
    RidePermitId ridePermitId,
    UnlockCode unlockCode,
    Location location,
    SlotNumber slotNumber
) {}
