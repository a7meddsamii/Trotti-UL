package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidTransferException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Transfer {
    private final TransferId transferId;
    private final Idul technicianId;
    private final Map<ScooterId, Boolean> scootersTransferCompletedState;

    public Transfer(
            TransferId transferId,
            Idul technicianId,
            Map<ScooterId, Boolean> scootersTransferCompletedState) {
        this.transferId = transferId;
        this.technicianId = technicianId;
        this.scootersTransferCompletedState = scootersTransferCompletedState;
    }

    public List<ScooterId> unload(Idul technicianId, int numberOfScooters) {
        validateUnloadOperation(technicianId, numberOfScooters);
        List<ScooterId> scootersToDeposit =
                getRemainingScootersToMove().subList(0, numberOfScooters);
        scootersToDeposit.forEach(scooterId -> scootersTransferCompletedState.put(scooterId, true));

        return scootersToDeposit;
    }

    private boolean isCompleted() {
        return getRemainingScootersToMove().isEmpty();
    }

    private List<ScooterId> getRemainingScootersToMove() {
        List<ScooterId> remainingScooters = new ArrayList<>();

        for (Map.Entry<ScooterId, Boolean> entry : scootersTransferCompletedState.entrySet()) {
            if (!entry.getValue()) {
                remainingScooters.add(entry.getKey());
            }
        }

        return remainingScooters;
    }

    private void validateUnloadOperation(Idul technicianId, int numberOfScooters) {
        if (!this.technicianId.equals(technicianId)) {
            throw new InvalidTransferException(
                    "Technician " + technicianId + " is not assigned to this transfer");
        }

        if (isCompleted()) {
            throw new InvalidTransferException("Transfer is already completed");
        }

        if (numberOfScooters <= 0 || numberOfScooters > getRemainingScootersToMove().size()) {
            throw new InvalidTransferException(
                    "Not enough scooters remaining to unload: " + numberOfScooters);
        }
    }
}
