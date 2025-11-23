package ca.ulaval.glo4003.trotti.billing.domain.order.factory;

import ca.ulaval.glo4003.trotti.billing.domain.order.entities.RidePermitItem;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.ItemId;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;

public class OrderItemFactory {
    public RidePermitItem create(MaximumDailyTravelTime maximumTravelingTime, Session session,
            BillingFrequency billingFrequency) {
        return new RidePermitItem(ItemId.randomId(), maximumTravelingTime, session,
                billingFrequency);
    }
}
