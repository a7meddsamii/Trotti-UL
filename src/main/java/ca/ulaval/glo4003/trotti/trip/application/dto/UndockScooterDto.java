package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;

public record UndockScooterDto(
        Location location,
        SlotNumber slotNumber
) {}