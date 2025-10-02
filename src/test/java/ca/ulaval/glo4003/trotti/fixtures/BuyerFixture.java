package ca.ulaval.glo4003.trotti.fixtures;

import static ca.ulaval.glo4003.trotti.fixtures.AccountFixture.*;

import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.Cart;
import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import org.mockito.Mockito;

public class BuyerFixture {
	
	private final CreditCard creditCard = Mockito.mock(CreditCard.class);
	
	public Buyer buildWithPaymentMethod() {
		return new Buyer(AN_IDUL, A_NAME, AN_EMAIL, new Cart());
	}
	
	public Buyer buildWithoutPaymentMethod() {
		return new Buyer(AN_IDUL, A_NAME, AN_EMAIL, new Cart(), creditCard);
	}
}
