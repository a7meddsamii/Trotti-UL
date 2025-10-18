package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.TripBook;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Wallet;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.RidePermitRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TravelerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TripRecord;
import java.util.List;

public class TravelerPersistenceMapper {

    public TravelerRecord toRecord(Traveler traveler) {
        List<RidePermitRecord> ridePermitRecords =
                traveler.getWalletPermits().stream().map(this::toRidePermitRecord).toList();

        List<TripRecord> trips = traveler.getBookTrips().stream().map(this::toTripRecord).toList();

        return new TravelerRecord(traveler.getIdul(), traveler.getEmail(), ridePermitRecords,
                trips);
    }

    public Traveler toDomain(TravelerRecord travelerRecord) {
        List<RidePermit> ridePermits =
                travelerRecord.ridePermits().stream().map(this::toRidePermitDomain).toList();

        Wallet wallet = new Wallet(ridePermits);

        List<Trip> trips = travelerRecord.unfinishedTrips().stream().map(this::toTripDomain).toList();

        TripBook tripBook = new TripBook(trips);

        return new Traveler(travelerRecord.idul(), travelerRecord.email(), wallet, tripBook);
    }

    private RidePermit toRidePermitDomain(RidePermitRecord ridePermitRecord) {
        return new RidePermit(ridePermitRecord.permitId(), ridePermitRecord.idul(),
                ridePermitRecord.session());
    }

    private Trip toTripDomain(TripRecord tripRecord) {
        return new Trip(tripRecord.id(), tripRecord.startDateTime(), tripRecord.ridePermitId(),
                tripRecord.travelerIdul(), tripRecord.scooterId());
    }

    private RidePermitRecord toRidePermitRecord(RidePermit ridePermit) {
        return new RidePermitRecord(ridePermit.getId(), ridePermit.getIdul(),
                ridePermit.getSession());
    }

    private TripRecord toTripRecord(Trip trip) {
        return new TripRecord(trip.getId(), trip.getStartTime(), trip.getRidePermit(),
                trip.getTravelerIdul(), trip.getScooterId());
    }

}
