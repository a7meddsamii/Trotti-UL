package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;

public record EndTripDto(
        Idul idul,
        Location location,
        SlotNumber slotNumber
) {}
