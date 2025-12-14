package ca.ulaval.glo4003.trotti.fleet.api.dto;

import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;

public record RetrieveScooterRequest(
		Location location,
		SlotNumber slotNumber
) {}