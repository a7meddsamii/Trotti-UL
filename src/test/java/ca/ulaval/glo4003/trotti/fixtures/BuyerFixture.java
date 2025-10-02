package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Cart;

public class BuyerFixture {

    public static final String A_NAME = "Camavinga";
    public static final Idul AN_IDUL_STRING = Idul.from("JD12345");
    public static final Email AN_EMAIL_STRING = Email.from("john.doe@ulaval.ca");

    public Buyer buildWithPaymentMethod() {
        return new Buyer(AN_IDUL_STRING, A_NAME, AN_EMAIL_STRING, new Cart(), new CreditCardFixture().build());
    }

    public Buyer buildWithoutPaymentMethod() {
        return new Buyer(AN_IDUL_STRING, A_NAME, AN_EMAIL_STRING, new Cart(), null);
    }
}
