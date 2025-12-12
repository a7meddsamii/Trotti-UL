package ca.ulaval.glo4003.trotti.fleet.domain.factories;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.entities.Transfer;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferFactory {

    public Transfer create(Idul technicianId, List<ScooterId> scooterIds) {
        Map<ScooterId, Boolean> transferState = initializeTransferState(scooterIds);

        return new Transfer(TransferId.randomId(), technicianId, transferState);
    }

    private static Map<ScooterId, Boolean> initializeTransferState(List<ScooterId> scooterIds) {
        Map<ScooterId, Boolean> state = new HashMap<>();

        for (ScooterId scooterId : scooterIds) {
            state.put(scooterId, false);
        }

        return state;
    }
}
