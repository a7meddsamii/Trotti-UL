package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RidePermitRepository {
    void save(RidePermit order);

    void saveAll(List<RidePermit> ridePermits);

    Optional<RidePermit> findById(RidePermitId ridePermitId);
	
	Optional<RidePermit> findByRiderIdAndRidePermitId(Idul riderId, RidePermitId ridePermitId);

    List<RidePermit> findAllByIdul(Idul idul);

    List<RidePermit> findAllByDate(LocalDate date);

    List<RidePermit> findAllBySession(Session session);
}
