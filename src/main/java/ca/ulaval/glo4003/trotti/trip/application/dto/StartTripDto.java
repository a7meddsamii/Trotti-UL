package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;

public record StartTripDto(
        Idul idul,
        RidePermitId ridePermitId,
        String unlockCode,
        Location location,
        SlotNumber slotNumber
) {}
