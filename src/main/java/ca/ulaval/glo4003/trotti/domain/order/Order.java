package ca.ulaval.glo4003.trotti.domain.order;

import java.util.List;

public class Order {
    private final List<Pass> passList;
    private final BillingFrequency billingFrequency;
    private final OrderStatus orderStatus;

    public Order(
            List<Pass> passList,
            BillingFrequency billingFrequency,
            OrderStatus orderStatus
    ) {
        this.passList = passList;
        this.billingFrequency = billingFrequency;
        this.orderStatus = orderStatus;
    }

    public List<Pass> getPassList() {
        return passList;
    }

    public BillingFrequency getBillingFrequency() {
        return billingFrequency;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
