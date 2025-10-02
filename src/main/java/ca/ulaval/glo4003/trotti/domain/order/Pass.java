package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.payment.values.Money;

public class Pass {
    private final MaximumDailyTravelTime maximumTravelingTime;
    private final Session session;
    private final BillingFrequency billingFrequency;
    private final Id id;
    private Idul owner;

    public Pass(
            MaximumDailyTravelTime maximumTravelingTime,
            Session session,
            BillingFrequency billingFrequency) {
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
        this.id = Id.randomId();
    }

    public Pass(
            MaximumDailyTravelTime maximumTravelingTime,
            Session session,
            BillingFrequency billingFrequency,
            Id id) {
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
        this.id = id;
    }

    public Pass(
            MaximumDailyTravelTime maximumTravelingTime,
            Session session,
            BillingFrequency billingFrequency,
            Idul owner,
            Id id) {
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
        this.owner = owner;
        this.id = id;
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

    public Id getId() {
        return id;
    }

    public Idul getIdul() {
        return owner;
    }

    public boolean linkToBuyer(Idul idul) {
        if (this.owner != null) {
            return false;
        }

        this.owner = idul;
        return true;
    }

    public Idul getBuyerIdul() {
        return owner;
    }

    public Money calculateAmount() {
        return maximumTravelingTime.calculateAmount();
    }

    public boolean isPurchased() {
        return owner != null;
    }

    @Override
    public String toString() {
        return "Pass ID: " + id.toString() + ", Maximum traveling time: "
                + maximumTravelingTime.toString() + ", Session: " + session.toString()
                + ", Billing frequency: " + billingFrequency.toString() + ", Cost: "
                + calculateAmount().toString();
    }
}
