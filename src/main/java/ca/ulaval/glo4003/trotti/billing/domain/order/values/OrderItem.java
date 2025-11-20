package ca.ulaval.glo4003.trotti.billing.domain.order.values;

import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.util.Objects;

public class OrderItem {
    private final ItemId itemId;
    private final MaximumDailyTravelTime maximumTravelingTime;
    private final Session session;
    private final BillingFrequency billingFrequency;

    public OrderItem of(ItemId itemId, MaximumDailyTravelTime maximumTravelingTime, Session session,
            BillingFrequency billingFrequency) {
        return new OrderItem(itemId, maximumTravelingTime, session, billingFrequency);
    }

    private OrderItem(
            ItemId itemId,
            MaximumDailyTravelTime maximumTravelingTime,
            Session session,
            BillingFrequency billingFrequency) {
        this.itemId = itemId;
        this.maximumTravelingTime = maximumTravelingTime;
        this.session = session;
        this.billingFrequency = billingFrequency;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        OrderItem orderItem = (OrderItem) o;
        return itemId.equals(orderItem.itemId)
                && maximumTravelingTime.equals(orderItem.maximumTravelingTime)
                && session.equals(orderItem.session)
                && billingFrequency == orderItem.billingFrequency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, maximumTravelingTime, session, billingFrequency);
    }

    public ItemId getItemId() {
        return itemId;
    }

    public Money getCost() {
        return maximumTravelingTime.calculateAmount();
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
