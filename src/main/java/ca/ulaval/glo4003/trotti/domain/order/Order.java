package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Money;
import java.math.BigDecimal;
import java.util.List;

public class Order {
    private final Idul idul;
    private final List<Pass> passList;

    public Order(Idul idul, List<Pass> passList) {
        this.idul = idul;
        this.passList = passList;
    }

    public Idul getIdul() {
        return idul;
    }

    public List<Pass> getPassList() {
        return passList;
    }

    public Money getTotal() {
        Money total = Money.from(new BigDecimal(0));
        for (Pass pass : passList) {
            total = total.add(pass.getTotal());
        }

        return total;
    }

    public String getInvoice() {
        // Return String of invoice
        return "Order's invoice";
    }
}
