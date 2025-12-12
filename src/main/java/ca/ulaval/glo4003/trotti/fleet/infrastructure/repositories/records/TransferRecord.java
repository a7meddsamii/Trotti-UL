package ca.ulaval.glo4003.trotti.fleet.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.fleet.domain.values.TransferId;

import java.util.Map;

public record TransferRecord(TransferId transferId, Idul technicianId, Map<ScooterId, Boolean> scootersTransferCompletedState) {
}
