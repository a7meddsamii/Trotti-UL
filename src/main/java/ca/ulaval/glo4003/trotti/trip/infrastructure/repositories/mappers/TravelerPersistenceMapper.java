package ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers;

import ca.ulaval.glo4003.trotti.trip.domain.entities.RidePermit;
import ca.ulaval.glo4003.trotti.trip.domain.entities.Trip;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.RidePermitWallet;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.RidePermitRecord;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TravelerRecord;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TripRecord;
import java.util.List;
import java.util.Optional;

@Deprecated
public class TravelerPersistenceMapper {

    private final TripPersistenceMapper tripPersistenceMapper;

    public TravelerPersistenceMapper(TripPersistenceMapper tripPersistenceMapper) {
        this.tripPersistenceMapper = tripPersistenceMapper;
    }

    public TravelerRecord toRecord(Traveler traveler) {
        List<RidePermitRecord> ridePermitRecords =
                traveler.getWalletPermits().stream().map(this::toRidePermitRecord).toList();

        TripRecord ongoingTrip =
                traveler.getOngoingTrip().map(tripPersistenceMapper::toRecord).orElse(null);

        return new TravelerRecord(traveler.getIdul(), traveler.getEmail(), ridePermitRecords,
                ongoingTrip);
    }

    public Traveler toDomain(TravelerRecord travelerRecord) {
        List<RidePermit> ridePermits =
                travelerRecord.ridePermits().stream().map(this::toRidePermitDomain).toList();

        RidePermitWallet ridePermitWallet = new RidePermitWallet(ridePermits);

        if (Optional.ofNullable(travelerRecord.ongoingTrip()).isPresent()) {
            Trip trip = tripPersistenceMapper.toDomain(travelerRecord.ongoingTrip());
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

    private RidePermitRecord toRidePermitRecord(RidePermit ridePermit) {
        return new RidePermitRecord(ridePermit.getId(), ridePermit.getIdul(),
                ridePermit.getSession());
    }

}
