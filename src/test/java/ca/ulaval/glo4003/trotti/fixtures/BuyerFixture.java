package ca.ulaval.glo4003.trotti.fixtures;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.*;

import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Cart;

public class BuyerFixture {

    public Buyer buildWithPaymentMethod() {
        return new Buyer(AN_IDUL, A_NAME, AN_EMAIL, new Cart(), new CreditCardFixture().build());
    }

    public Buyer buildWithoutPaymentMethod() {
        return new Buyer(AN_IDUL, A_NAME, AN_EMAIL, new Cart(), null);
    }
}
