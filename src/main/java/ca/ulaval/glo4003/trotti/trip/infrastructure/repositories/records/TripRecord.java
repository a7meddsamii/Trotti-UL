package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.Location;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import ca.ulaval.glo4003.trotti.trip.domain.values.TripId;

import java.time.LocalDateTime;

public record TripRecord(TripId tripId,
                         Idul idul,
                         RidePermitId ridePermitId,
                         ScooterId scooterId,
                         LocalDateTime startTime,
                         Location startLocation,
                         LocalDateTime endTime,
                         Location endLocation) {}
