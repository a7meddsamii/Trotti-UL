package ca.ulaval.glo4003.trotti.fleet.application.dto;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.SlotNumber;
import java.util.List;

public record StartTransferDto(
        Idul technicianId,
        Location sourceStation,
        List<SlotNumber> sourceSlots) {}
