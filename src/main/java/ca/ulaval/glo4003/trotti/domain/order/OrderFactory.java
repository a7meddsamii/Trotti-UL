package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.util.List;

public class OrderFactory {
    public Order create(Idul idul, List<Pass> passList) {
        validate(idul, passList);
        Id id = Id.randomId();
        return new Order(idul, passList, id);
    }

    private void validate(Idul idul, List<Pass> passList) {
        if (idul == null) {
            throw new InvalidParameterException("Idul cannot be null.");
        }

        if (passList.isEmpty()) {
            throw new InvalidParameterException("List of passes cannot be empty.");
        }
    }
}
