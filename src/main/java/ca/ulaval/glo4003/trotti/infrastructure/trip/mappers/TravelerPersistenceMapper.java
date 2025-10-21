package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Trip;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.RidePermitWallet;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.entities.traveler.TripWallet;
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

        RidePermitWallet ridePermitWallet = new RidePermitWallet(ridePermits);

        List<Trip> trips =
                travelerRecord.unfinishedTrips().stream().map(this::toTripDomain).toList();

        TripWallet tripWallet = new TripWallet(trips);

        return new Traveler(travelerRecord.idul(), travelerRecord.email(), ridePermitWallet,
                tripWallet);
    }

    private RidePermit toRidePermitDomain(RidePermitRecord ridePermitRecord) {
        return new RidePermit(ridePermitRecord.permitId(), ridePermitRecord.idul(),
                ridePermitRecord.session());
    }

    private Trip toTripDomain(TripRecord tripRecord) {
        return new Trip(tripRecord.startDateTime(), tripRecord.ridePermitId(),
                tripRecord.travelerIdul(), tripRecord.scooterId());
    }

    private RidePermitRecord toRidePermitRecord(RidePermit ridePermit) {
        return new RidePermitRecord(ridePermit.getId(), ridePermit.getIdul(),
                ridePermit.getSession());
    }

    private TripRecord toTripRecord(Trip trip) {
        return new TripRecord(trip.getStartTime(), trip.getRidePermit(),
                trip.getTravelerIdul(), trip.getScooterId());
    }

}
