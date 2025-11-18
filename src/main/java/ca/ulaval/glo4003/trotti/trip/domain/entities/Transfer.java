package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InsufficientScootersInTransitException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transfer {
    private final TransferId transferId;
    private final Location sourceStation;
    private final Idul technicianId;
    private final Map<ScooterId, Boolean> scooters;

    public Transfer(TransferId transferId, Location sourceStation, Idul technicianId) {
        this.transferId = transferId;
        this.sourceStation = sourceStation;
        this.technicianId = technicianId;
        this.scooters = new HashMap<>();
    }

    public void unload(Station destination, List<SlotNumber> destinationSlots) {
        if (destinationSlots.size() > scooters.size()) {
            throw new InsufficientScootersInTransitException("Cannot unload " + destinationSlots.size() + " scooters, as there are only " +
                    scooters.size() + " scooters in transit");
        }

        List<ScooterId> availableScooters = getScootersInTransit();

        for (int i = 0; i < destinationSlots.size(); i++) {
            ScooterId scooterId = availableScooters.get(i);
            SlotNumber slot = destinationSlots.get(i);
            destination.returnScooter(slot, scooterId);
            scooters.put(scooterId, true);
        }
    }

    private List<ScooterId> getScootersInTransit() {
        return scooters.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey)
                .toList();
    }

    public TransferId getTransferId() {
        return transferId;
    }

    public Location getSourceStation() {
        return sourceStation;
    }

    public Idul getTechnicianId() {
        return technicianId;
    }

    public Map<ScooterId, Boolean> getScooters() {
        return scooters;
    }
}
