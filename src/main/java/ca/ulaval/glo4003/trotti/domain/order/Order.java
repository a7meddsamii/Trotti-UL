package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import java.util.List;

public class Order {
    private final Idul idul;
    private final List<Pass> passList;
    private final BillingFrequency billingFrequency;

    public Order(Idul idul, List<Pass> passList, BillingFrequency billingFrequency) {
        this.idul = idul;
        this.passList = passList;
        this.billingFrequency = billingFrequency;
    }

    public Idul getIdul() {
        return idul;
    }

    public List<Pass> getPassList() {
        return passList;
    }

    public BillingFrequency getBillingFrequency() {
        return billingFrequency;
    }
}
