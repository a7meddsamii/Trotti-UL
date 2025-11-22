package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.factory;

import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.entities.RidePermit;
import ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values.RidePermitId;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.order.OrderItemSnapshot;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RidePermitFactory {

    public RidePermit create(Idul riderId, Session session,
								   Duration maxDailyTravelTime,
								   BillingFrequency billingFrequency){
		RidePermitId ridePermitId = RidePermitId.randomId();
		return new RidePermit(ridePermitId, riderId,session,maxDailyTravelTime);
	}
}