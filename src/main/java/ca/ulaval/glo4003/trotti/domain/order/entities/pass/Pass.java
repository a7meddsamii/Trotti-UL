package ca.ulaval.glo4003.trotti.domain.order.entities.pass;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.payment.values.money.Money;
import ca.ulaval.glo4003.trotti.domain.order.values.BillingFrequency;
import ca.ulaval.glo4003.trotti.domain.order.values.MaximumDailyTravelTime;
import ca.ulaval.glo4003.trotti.domain.order.values.PassId;
import ca.ulaval.glo4003.trotti.domain.order.values.Session;

public class Pass {
    private final MaximumDailyTravelTime maximumTravelingTime;
    private final Session session;
    private final BillingFrequency billingFrequency;
    private final PassId id;
    private Idul owner;

    public Pass(
            MaximumDailyTravelTime maximumTravelingTime,
            Session session,
            BillingFrequency billingFrequency) {
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
        this.id = PassId.randomId();
    }

    public Pass(
            MaximumDailyTravelTime maximumTravelingTime,
            Session session,
            BillingFrequency billingFrequency,
            PassId id) {
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
        this.id = id;
    }

    public Pass(
            MaximumDailyTravelTime maximumTravelingTime,
            Session session,
            BillingFrequency billingFrequency,
            PassId id,
            Idul owner) {
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

    public Idul getBuyerIdul() {
        return owner;
    }

    public PassId getId() {
        return id;
    }

    public Pass linkToBuyer(Idul idul) {
        if (this.owner != null) {
            return this;
        }

        this.owner = idul;
        return new Pass(this.maximumTravelingTime, this.session, this.billingFrequency, this.id,
                this.owner);
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
