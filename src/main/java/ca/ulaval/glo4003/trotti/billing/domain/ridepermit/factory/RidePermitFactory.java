package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory;

import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.order.OrderItemSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RidePermitFactory {

    public List<RidePermit> create(Idul riderId, List<OrderItemSnapshot> orderItems) {
		List<RidePermit> ridePermits = new ArrayList<>();
		
		for (OrderItemSnapshot orderItem : orderItems) {
			RidePermitId ridePermitId = RidePermitId.randomId();
			ridePermits.add(new RidePermit(ridePermitId, riderId,orderItem.session(),orderItem.maxDailyTravelTime()));
		}
		
		return ridePermits;
    }
}
