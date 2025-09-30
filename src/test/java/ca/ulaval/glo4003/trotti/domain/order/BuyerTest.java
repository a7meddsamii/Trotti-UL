package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.payment.CreditCard;
import ca.ulaval.glo4003.trotti.fixtures.BuyerFixture;
import ca.ulaval.glo4003.trotti.fixtures.CreditCardFixture;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BuyerTest {
    @Test
    void givenBuyerWithPaymentMethod_whenGetPaymentMethod_thenReturnsOptional() {
        Buyer buyer = new BuyerFixture().buildWithPaymentMethod();

        Assertions.assertTrue(buyer.getPaymentMethod().isPresent());
    }

    @Test
    void givenBuyerWithoutPaymentMethod_whenGetPaymentMethod_thenReturnsEmpty() {
        Buyer buyer = new BuyerFixture().buildWithoutPaymentMethod();

        Assertions.assertEquals(Optional.empty(), buyer.getPaymentMethod());
    }

    @Test
    void givenBuyer_whenUpdatePaymentMethod_thenPaymentMethodIsUpdated() {
        Buyer buyer = new BuyerFixture().buildWithoutPaymentMethod();
        CreditCard creditCard = new CreditCardFixture().build();

        buyer.updatePaymentMethod(creditCard);

        Assertions.assertTrue(buyer.getPaymentMethod().isPresent());
        Assertions.assertEquals(creditCard, buyer.getPaymentMethod().get());
    }

    @Test
    void givenBuyerWithPaymentMethod_whenDeletePaymentMethod_thenPaymentMethodIsEmpty() {
        Buyer buyer = new BuyerFixture().buildWithPaymentMethod();

        buyer.deletePaymentMethod();

        Assertions.assertEquals(Optional.empty(), buyer.getPaymentMethod());
    }
}
