package ca.ulaval.glo4003.trotti.infrastructure.trip.mappers;

import ca.ulaval.glo4003.trotti.domain.trip.entities.RidePermit;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler.Traveler;
import ca.ulaval.glo4003.trotti.domain.trip.entities.Traveler.Wallet;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.RidePermitRecord;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TravelerRecord;
import java.util.List;

public class TravelerPersistenceMapper {

    public TravelerRecord toRecord(Traveler traveler) {
        List<RidePermitRecord> ridePermitRecords =
                traveler.getWallet().stream().map(this::toRidePermitRecord).toList();

        return new TravelerRecord(traveler.getIdul(), traveler.getEmail(), ridePermitRecords);
    }

    public Traveler toDomain(TravelerRecord travelerRecord) {
        List<RidePermit> ridePermits =
                travelerRecord.ridePermits().stream().map(this::toRidePermitDomain).toList();

        new Wallet(ridePermits);
        return new Traveler(travelerRecord.idul(), travelerRecord.email(), new Wallet(ridePermits));
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
