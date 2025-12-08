package ca.ulaval.glo4003.trotti.fleet.domain.entities;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.exceptions.InvalidTransferException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transfer {
    private final Idul technicianId;
    private final List<Scooter> scootersToMove;

    public Transfer(Idul technicianId, List<Scooter> scootersToMove) {
        this.technicianId = technicianId;
        this.scootersToMove = scootersToMove;
    }

    public List<Scooter> unload(Idul technicianId, int numberOfScooters) {
        validateUnloadOperation(technicianId, numberOfScooters);
        List<Scooter> scootersToDeposit =
                new ArrayList<>(scootersToMove.subList(0, numberOfScooters));
        scootersToMove.subList(0, numberOfScooters).clear();
        return scootersToDeposit;
    }

    public boolean isCompleted() {
        return scootersToMove.isEmpty();
    }

    public List<Scooter> getScootersToMove() {
        return Collections.unmodifiableList(scootersToMove);
    }
	
	private void validateUnloadOperation(Idul technicianId, int numberOfScooters) {
		if (!this.technicianId.equals(technicianId)) {
			throw new InvalidTransferException(
					"Technician " + technicianId + " is not assigned to this transfer");
		}
		
		if (numberOfScooters <= 0 || numberOfScooters > scootersToMove.size()) {
			throw new InvalidTransferException(
					"Invalid number of scooters to unload: " + numberOfScooters);
		}
	}
}
