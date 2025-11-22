package ca.ulaval.glo4003.trotti.trip.domain.entities;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.InsufficientScootersInTransitException;
import ca.ulaval.glo4003.trotti.trip.domain.exceptions.TechnicianNotInChargeException;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Transfer {
    private final TransferId transferId;
    private final Location sourceStation;
    private final Idul technicianId;
    private final Map<ScooterId, Boolean> scootersMoved;

    public static Transfer start(Idul technicianId, Location sourceStation,
            Set<ScooterId> scooters) {
        TransferId transferId = TransferId.randomId();
        Map<ScooterId, Boolean> scootersMoved = new HashMap<>();
        scooters.forEach(scooterId -> scootersMoved.put(scooterId, false));

        return new Transfer(transferId, sourceStation, technicianId, scootersMoved);
    }

    public static Transfer rehydrate(TransferId transferId, Location sourceStation,
            Idul technicianId, Map<ScooterId, Boolean> scootersMoved) {
        return new Transfer(transferId, sourceStation, technicianId, scootersMoved);
    }

    private Transfer(
            TransferId transferId,
            Location sourceStation,
            Idul technicianId,
            Map<ScooterId, Boolean> scootersMoved) {
        this.transferId = transferId;
        this.sourceStation = sourceStation;
        this.technicianId = technicianId;
        this.scootersMoved = scootersMoved;
    }

    public List<ScooterId> unload(Idul technicianId, int numberOfScooters) {
        validateUnload(technicianId, numberOfScooters);

        List<ScooterId> scootersInTransit = getScootersInTransit();
        List<ScooterId> scootersToUnload = scootersInTransit.subList(0, numberOfScooters);
        scootersToUnload.forEach(scooterId -> scootersMoved.put(scooterId, true));

        return scootersToUnload;
    }

    private void validateUnload(Idul technicianId, int numberOfScooters) {
        if (!this.technicianId.equals(technicianId)) {
            throw new TechnicianNotInChargeException(
                    "Technician with idul " + technicianId + " is not in charge of this transfer");

        }

        List<ScooterId> scootersInTransit = getScootersInTransit();
        if (numberOfScooters > scootersInTransit.size()) {
            throw new InsufficientScootersInTransitException(
                    "Cannot unload " + numberOfScooters + " scooters, as there are only "
                            + scootersInTransit.size() + " scooters left in transit");
        }
    }

    private List<ScooterId> getScootersInTransit() {
        return scootersMoved.entrySet().stream().filter(entry -> !entry.getValue())
                .map(Map.Entry::getKey).toList();
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

    public Map<ScooterId, Boolean> getScootersMovedCopy() {
        return new HashMap<>(scootersMoved);
    }

    public int getScootersInTransitCount() {
        return getScootersInTransit().size();
    }
}
