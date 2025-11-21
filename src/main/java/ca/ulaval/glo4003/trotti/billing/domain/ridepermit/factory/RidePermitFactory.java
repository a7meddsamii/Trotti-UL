package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory;

import ca.ulaval.glo4003.trotti.billing.domain.order.entities.OrderItem;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

import java.util.ArrayList;
import java.util.List;

public class RidePermitFactory {

    public List<RidePermit> create(Idul riderId, List<OrderItem> orderItems) {
		List<RidePermit> ridePermits = new ArrayList<>();
		
		for (OrderItem orderItem : orderItems) {
			RidePermitId ridePermitId = RidePermitId.randomId();
			ridePermits.add(new RidePermit(ridePermitId, riderId,orderItem.getSession(),orderItem.getMaximumTravelingTime().getValue()));
		}
		
		return ridePermits;
    }
}
