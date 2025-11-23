package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;

public record RetrieveScooterRequest(
		Location location,
		SlotNumber slotNumber
) {}