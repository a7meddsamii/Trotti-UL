package ca.ulaval.glo4003.trotti.trip.application.dto;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.List;

public record UnloadScootersDto(TransferId transferId, Idul technicianId, Location destinationStation, List<SlotNumber> destinationSlots) {
}