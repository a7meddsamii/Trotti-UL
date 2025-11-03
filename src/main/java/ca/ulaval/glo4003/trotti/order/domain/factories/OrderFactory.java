package ca.ulaval.glo4003.trotti.order.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Order;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.order.domain.exceptions.InvalidOrderException;
import java.util.List;

public class OrderFactory {
    public Order create(Idul idul, List<Pass> passList) {
        validate(idul, passList);
        return new Order(idul, passList);
    }

    private void validate(Idul idul, List<Pass> passList) {
        if (idul == null) {
            throw new InvalidOrderException("Idul cannot be null.");
        }

        if (passList.isEmpty()) {
            throw new InvalidOrderException("List of passes cannot be empty.");
        }
    }
}
