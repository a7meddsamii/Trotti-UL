package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.trip.values.RidePermitId;
import ca.ulaval.glo4003.trotti.domain.trip.values.ScooterId;
import java.time.LocalDateTime;
import ca.ulaval.glo4003.trotti.domain.commons.Id;

public record TripRecord(LocalDateTime startDateTime, RidePermitId ridePermitId, Idul travelerIdul, ScooterId scooterId) {
import java.time.Instant;

public record TripRecord(Id tripId, Idul idulTraveler, Id ridePermitId, Instant startAt, Instant endAt) {
}
