package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import java.util.List;

public record InitiateTransferDto(Location sourceStation, Idul technicianId, List<SlotNumber> sourceSlots) {
}