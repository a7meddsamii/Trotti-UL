package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;

public class PassFactory {

    public Pass create(MaximumDailyTravelTime maximumDailyTravelTime, Session session,
            BillingFrequency billingFrequency) {
        return new Pass(maximumDailyTravelTime, session, billingFrequency);
    }
}
