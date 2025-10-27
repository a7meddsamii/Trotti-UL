package ca.ulaval.glo4003.trotti.application.trip.dto;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.values.SlotNumber;
import ca.ulaval.glo4003.trotti.domain.trip.entities.UnlockCode;
import ca.ulaval.glo4003.trotti.domain.trip.values.Location;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;

public record StartTripDto(
        Idul idul,
        RidePermitId ridePermitId,
        UnlockCode unlockCode,
        Location location,
        SlotNumber slotNumber
) {}
