package ca.ulaval.glo4003.trotti.api.trip.dto.requests;

import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;

public record EndTripRequest(
        Location location,
        SlotNumber slotNumber
) {
}
