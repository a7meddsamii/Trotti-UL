package ca.ulaval.glo4003.trotti.order.fixtures;

import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Cart;
import ca.ulaval.glo4003.trotti.payment.fixture.CreditCardFixture;

public class BuyerFixture {

    public static final String A_NAME = "Camavinga";
    public static final Idul AN_IDUL_STRING = Idul.from("JD12345");
    public static final Email AN_EMAIL_STRING = Email.from("john.doe@ulaval.ca");

    public Buyer buildWithPaymentMethod() {
        return new Buyer(AN_IDUL_STRING, A_NAME, AN_EMAIL_STRING, new Cart(),
                new CreditCardFixture().build());
    }

    public Buyer buildWithoutPaymentMethod() {
        return new Buyer(AN_IDUL_STRING, A_NAME, AN_EMAIL_STRING, new Cart(), null);
    }
}
