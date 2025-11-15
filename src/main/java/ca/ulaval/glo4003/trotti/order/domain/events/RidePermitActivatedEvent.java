package ca.ulaval.glo4003.trotti.order.domain.events;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.events.Event;

public class RidePermitActivatedEvent extends Event {

    private final String ridePermitId;
    private final String session;
    private final String billingFrequency;
    private final String maximumDailyTravelTime;

    public RidePermitActivatedEvent(
            Idul idul,
            String ridePermitId,
            String session,
            String billingFrequency,
            String maximumDailyTravelTime) {
        super(idul, "ride_permit.activated");
        this.ridePermitId = ridePermitId;
        this.session = session;
        this.billingFrequency = billingFrequency;
        this.maximumDailyTravelTime = maximumDailyTravelTime;
    }

    public String getRidePermitId() {
        return ridePermitId;
    }

    public String getSession() {
        return session;
    }

    public String getBillingFrequency() {
        return billingFrequency;
    }

    public String getMaximumDailyTravelTime() {
        return maximumDailyTravelTime;
    }
}
