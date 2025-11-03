package ca.ulaval.glo4003.trotti.order.domain.factories;

import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.order.domain.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.order.domain.values.Session;

public class PassFactory {

    public Pass create(MaximumDailyTravelTime maximumDailyTravelTime, Session session,
            BillingFrequency billingFrequency) {
        return new Pass(maximumDailyTravelTime, session, billingFrequency);
    }
}
