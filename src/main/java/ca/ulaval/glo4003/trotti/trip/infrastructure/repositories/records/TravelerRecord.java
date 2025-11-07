package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;
import java.util.List;

public record TravelerRecord(Idul idul, Email email, List<RidePermitRecord> ridePermits, TripRecord ongoingTrip) {
}
