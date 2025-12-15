package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.fleet.domain.values.Location;
import ca.ulaval.glo4003.trotti.fleet.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.*;
import java.time.LocalDateTime;

public record TripRecord(TripId tripId,
                         Idul idul,
                         RidePermitId ridePermitId,
                         ScooterId scooterId,
                         LocalDateTime startTime,
                         Location startLocation,
                         LocalDateTime endTime,
                         Location endLocation,
                         TripStatus tripStatus) {}
