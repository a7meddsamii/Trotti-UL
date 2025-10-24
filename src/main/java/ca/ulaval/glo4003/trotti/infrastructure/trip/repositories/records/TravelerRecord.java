package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.util.List;
import java.util.Optional;

public record TravelerRecord(Idul idul, Email email, List<RidePermitRecord> ridePermits, TripRecord ongoingTrip) {
}
