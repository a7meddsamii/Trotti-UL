package ca.ulaval.glo4003.trotti.fleet.application.dto;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.util.List;

public record UnloadTransferDto(
        TransferId transferId,
        Idul technicianId,
        Location destinationStation,
        List<SlotNumber> destinationSlots
) {}
