package ca.ulaval.glo4003.trotti.fleet.domain.exceptions;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;

public class TransferNotFoundException extends NotFoundException {

    public TransferNotFoundException(TransferId transferId) {
        super("Transfer not found with id: " + transferId);
    }
}
