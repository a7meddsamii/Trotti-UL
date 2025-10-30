package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.RidePermitWallet;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.RidePermitRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TravelerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TripRecord;
import java.util.List;
import java.util.Optional;

public class TravelerPersistenceMapper {

    public TravelerRecord toRecord(Traveler traveler) {
        List<RidePermitRecord> ridePermitRecords =
                traveler.getWalletPermits().stream().map(this::toRidePermitRecord).toList();

        TripRecord ongoingTrip = toTripRecord(traveler.getOngoingTrip());

        return new TravelerRecord(traveler.getIdul(), traveler.getEmail(), ridePermitRecords,
                ongoingTrip);
    }

    public Traveler toDomain(TravelerRecord travelerRecord) {
        List<RidePermit> ridePermits =
                travelerRecord.ridePermits().stream().map(this::toRidePermitDomain).toList();

        RidePermitWallet ridePermitWallet = new RidePermitWallet(ridePermits);

        if (Optional.ofNullable(travelerRecord.ongoingTrip()).isPresent()) {
            Trip trip = toTripDomain(travelerRecord.ongoingTrip());
            return new Traveler(travelerRecord.idul(), travelerRecord.email(), ridePermitWallet,
                    trip);
        } else {
            return new Traveler(travelerRecord.idul(), travelerRecord.email(), ridePermitWallet);
        }

    }

    private RidePermit toRidePermitDomain(RidePermitRecord ridePermitRecord) {
        return new RidePermit(ridePermitRecord.permitId(), ridePermitRecord.idul(),
                ridePermitRecord.session());
    }

    private Trip toTripDomain(TripRecord tripRecord) {
        return new Trip(tripRecord.startTime(), tripRecord.ridePermitId(),
                tripRecord.travelerIdul(), tripRecord.scooterId());
    }

    private RidePermitRecord toRidePermitRecord(RidePermit ridePermit) {
        return new RidePermitRecord(ridePermit.getId(), ridePermit.getIdul(),
                ridePermit.getSession());
    }

    private TripRecord toTripRecord(Trip trip) {
        return new TripRecord(trip.getStartTime(), trip.getRidePermitId(), trip.getTravelerIdul(),
                trip.getScooterId(), trip.getEndTime());
    }

}
