package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TransferId;
import java.util.Map;

public record TransferRecord(
        TransferId transferId,
        Location location,
        Idul  technicianId,
        Map<ScooterId, Boolean> scootersMoved
) {}
