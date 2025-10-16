package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;

import java.time.LocalDateTime;

public record TripRecord(Id id, LocalDateTime startDateTime, Id ridePermitId, Idul travelerIdul, Id scooterId) {

}
