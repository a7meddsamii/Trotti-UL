package ca.ulaval.glo4003.trotti.domain.order;

public class Pass {
    private final MaximumDailyTravelTime maximumDailyTravelTime;
    private final Session session;
    private final BillingFrequency billingFrequency;

    public Pass(
            MaximumDailyTravelTime maximumDailyTravelTime,
            Session session,
            BillingFrequency billingFrequency) {
        this.maximumDailyTravelTime = maximumDailyTravelTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
    }

    public MaximumDailyTravelTime getMaximumTravelingTime() {
        return maximumDailyTravelTime;
    }

    public Session getSession() {
        return session;
    }

    public BillingFrequency getBillingFrequency() {
        return billingFrequency;
    }
}
