package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.List;

public interface RidePermitRepository {
    void save(RidePermit order);
	
	void saveAll(List<RidePermit> ridePermits);

    RidePermit findById(RidePermitId orderId);

    List<RidePermit> findAllByIdul(Idul idul);

    List<RidePermit> findAllByDate(LocalDate date);
	
	List<RidePermit> findAllBySession(Session session);
}
