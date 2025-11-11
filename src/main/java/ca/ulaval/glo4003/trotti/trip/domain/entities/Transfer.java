package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.order.domain.values.SlotNumber;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InsufficientScootersInTransitException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.ArrayList;
import java.util.List;

public class Transfer {
    private final TransferId transferId;
    private final Location sourceLocation;
    private final List<SlotNumber> sourceSlots;
    private final List<ScooterId> scootersInTransit;

    public Transfer(TransferId transferId, Location sourceLocation, List<SlotNumber> sourceSlots) {
        this.transferId = transferId;
        this.sourceLocation = sourceLocation;
        this.sourceSlots = List.copyOf(sourceSlots);
        this.scootersInTransit = new ArrayList<>();
    }

    public void addScootersToTransit(List<ScooterId> scooterIds) {
        this.scootersInTransit.addAll(scooterIds);
    }

    public List<ScooterId> removeScootersFromTransit(int numberOfScooters) {
        if (numberOfScooters > scootersInTransit.size()) {
            throw new InsufficientScootersInTransitException(
                "Cannot store " + numberOfScooters + " scooters, only " + scootersInTransit.size() + " in transit");
        }
        
        List<ScooterId> scootersToStore = new ArrayList<>();
        for (int i = 0; i < numberOfScooters; i++) {
            scootersToStore.add(scootersInTransit.removeFirst());
        }
        
        return scootersToStore;
    }

    public TransferId getTransferId() {
        return transferId;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public List<SlotNumber> getSourceSlots() {
        return sourceSlots;
    }

    public List<ScooterId> getScootersInTransit() {
        return List.copyOf(scootersInTransit);
    }

    public int getRemainingScooterCount() {
        return scootersInTransit.size();
    }
}