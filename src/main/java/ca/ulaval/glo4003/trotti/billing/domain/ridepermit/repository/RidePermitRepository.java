package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.repository;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.OrderStatus;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.order.domain.values.OrderId;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

public interface RidePermitRepository {
	void save(RidePermit order);
	
	RidePermit findById(RidePermitId orderId);
	
	List<RidePermit> findAllByIdul(Idul idul);
	
	List<RidePermit> findAllByDate(LocalDate date);
}
