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
            Id id,
            Idul owner) {
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
        this.id = id;
        this.owner = owner;
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

    public boolean isPurchased() {
        return owner != null;
    }

    public Money calculateAmount() {
        return maximumTravelingTime.calculateAmount();
    }

    public String generateInvoice() {
        return "\nPass ID : " + id.toString() + "\n" + "Maximum traveling time : "
                + maximumTravelingTime.toString() + "\n" + "Session : " + session.toString() + "\n"
                + "Billing frequency : " + billingFrequency.toString() + "\n" + "Cost : "
                + calculateAmount().toString() + "\n";
    }
}
