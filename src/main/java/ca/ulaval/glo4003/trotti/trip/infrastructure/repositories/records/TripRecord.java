package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.values.RidePermitId;
import ca.ulaval.glo4003.trotti.trip.domain.values.ScooterId;
import java.time.LocalDateTime;

public record TripRecord(LocalDateTime startTime, RidePermitId ridePermitId, Idul travelerIdul, ScooterId scooterId, LocalDateTime endTime) {}
