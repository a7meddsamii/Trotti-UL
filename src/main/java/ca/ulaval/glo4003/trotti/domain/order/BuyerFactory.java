package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;

public class BuyerFactory {
    public Buyer create(Idul idul, String name, Email email) {
        Cart cart = new Cart();
        return new Buyer(idul, name, email, cart);
    }

    private void validate(Idul idul, String name, Email email, Cart cart) {
        if (idul == null) {
            throw new InvalidParameterException("Idul cannot be null.");
        }

        if (StringUtils.isBlank(name)) {
            throw new InvalidParameterException("Name is missing, it cannot be null or empty.");
        }

        if (email == null) {
            throw new InvalidParameterException("Email cannot be null.");
        }

        if (cart == null) {
            throw new InvalidParameterException("Cart cannot be null.");
        }
    }
}
