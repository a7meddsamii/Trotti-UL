package ca.ulaval.glo4003.trotti.application.trip.dto;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;

public record EndTripDto(
        Idul idul,
        Location location,
        SlotNumber slotNumber
) {}
