package ca.ulaval.glo4003.trotti.fleet.application.dto;

import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;

public record RentScooterDto(
        Location location,
        SlotNumber slotNumber
) {}