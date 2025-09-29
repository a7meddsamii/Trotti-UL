package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;

public class Pass {
    private final MaximumDailyTravelTime maximumTravelingTime;
    private final Session session;
    private final BillingFrequency billingFrequency;

    public Pass(
            MaximumDailyTravelTime maximumTravelingTime,
            Session session,
            BillingFrequency billingFrequency) {
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
    }

    public MaximumDailyTravelTime getMaximumTravelingTime() {
        return maximumTravelingTime;
    }

    public Session getSession() {
        return session;
    }

    public BillingFrequency getBillingFrequency() {
        return billingFrequency;
    }
}
