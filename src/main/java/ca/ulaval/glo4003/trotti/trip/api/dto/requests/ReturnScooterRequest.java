package ca.ulaval.glo4003.trotti.trip.api.dto.requests;

import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;

public record ReturnScooterRequest(
		Location location,
		SlotNumber slotNumber,
		ScooterId scooterId
) {}