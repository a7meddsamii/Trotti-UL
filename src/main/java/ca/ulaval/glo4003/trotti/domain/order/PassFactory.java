package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;

public class PassFactory {
    public Pass create(MaximumDailyTravelTime maximumDailyTravelTime, Session session,
            BillingFrequency billingFrequency) {
        Id id = Id.randomId();
        return new Pass(maximumDailyTravelTime, session, billingFrequency, id);
    }
}
