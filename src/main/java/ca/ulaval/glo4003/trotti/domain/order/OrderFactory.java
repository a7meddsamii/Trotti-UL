package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.util.List;

public class OrderFactory {
    public Order create(Idul idul, List<Pass> passList) {
        validateIdul(idul);
        validatePassList(passList);
        Id id = Id.randomId();
        return new Order(idul, passList, id);
    }

    private void validateIdul(Idul idul) {
        if (idul == null) {
            throw new InvalidParameterException("Idul cannot be null.");
        }
    }

    private void validatePassList(List<Pass> passList) {
        if (passList.isEmpty()) {
            throw new InvalidParameterException("List of passes cannot be empty.");
        }
    }
}
