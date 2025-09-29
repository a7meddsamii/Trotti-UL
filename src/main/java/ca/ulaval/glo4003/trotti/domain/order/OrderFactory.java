package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.util.List;

public class OrderFactory {

    public OrderFactory() {}

    public Order create(Idul idul, List<Pass> passList) {
        return new Order(idul, passList);
    }
}
