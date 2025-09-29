package ca.ulaval.glo4003.trotti.domain.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.Order;

public interface OrderRepository {
    void save(Order order);

    Order getOrder(Idul idul, Id id);
}
