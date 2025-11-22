package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.SlotNumber;

public record StartTripDto(
        Idul idul,
        RidePermitId ridePermitId,
        String unlockCode,
        Location location,
        SlotNumber slotNumber
) {}
