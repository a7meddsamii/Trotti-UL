package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;

public record UndockScooterDto(
        Location location,
        SlotNumber slotNumber
) {}