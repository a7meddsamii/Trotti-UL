package ca.ulaval.glo4003.trotti.domain.order.entities.buyer;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Id;
import ca.ulaval.glo4003.trotti.domain.order.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.domain.payment.values.method.CreditCard;
import ca.ulaval.glo4003.trotti.fixtures.BuyerFixture;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BuyerTest {

    private static final String BUYER_NAME = "John Doe";

    private Idul idul;
    private Email email;
    private Cart cart;
    private CreditCard paymentMethod;
    private Pass pass;
    private Id passId;
    private Buyer buyer;

    @BeforeEach
    void setUp() {
        idul = Mockito.mock(Idul.class);
        email = Mockito.mock(Email.class);
        cart = Mockito.mock(Cart.class);
        paymentMethod = Mockito.mock(CreditCard.class);
        pass = Mockito.mock(Pass.class);
        passId = Mockito.mock(Id.class);

        buyer = new Buyer(idul, BUYER_NAME, email, cart, paymentMethod);
    }

    @Test
    void givenBuyerWithoutPaymentMethod_whenUpdatePaymentMethod_thenPaymentMethodIsSet() {
        Buyer buyerWithoutPaymentMethod = new BuyerFixture().buildWithPaymentMethod();

        buyerWithoutPaymentMethod.updatePaymentMethod(paymentMethod);

        Assertions.assertTrue(buyer.getPaymentMethod().isPresent());
        Assertions.assertEquals(paymentMethod, buyer.getPaymentMethod().get());
    }

    @Test
    void givenAnotherPaymentMethod_whenUpdatePaymentMethod_thenPaymentMethodIsUpdated() {
        CreditCard anotherPaymentMethod = Mockito.mock(CreditCard.class);

        buyer.updatePaymentMethod(anotherPaymentMethod);

        Assertions.assertTrue(buyer.getPaymentMethod().isPresent());
        Assertions.assertEquals(anotherPaymentMethod, buyer.getPaymentMethod().get());
    }

    @Test
    void givenBuyerWithPaymentMethod_whenDeletePaymentMethod_thenPaymentMethodIsEmpty() {
        buyer.deletePaymentMethod();

        Assertions.assertTrue(buyer.getPaymentMethod().isEmpty());
    }

    @Test
    void whenAddToCart_thenCartAddIsCalled() {
        Mockito.when(cart.add(pass)).thenReturn(true);

        boolean result = buyer.addToCart(pass);

        Assertions.assertTrue(result);
        Mockito.verify(cart).add(pass);
    }

    @Test
    void whenAddToCartFails_thenReturnsFalse() {
        Mockito.when(cart.add(pass)).thenReturn(false);

        boolean result = buyer.addToCart(pass);

        Assertions.assertFalse(result);
        Mockito.verify(cart).add(pass);
    }

    @Test
    void whenRemoveFromCart_thenCartRemoveIsCalled() {
        Mockito.when(cart.remove(passId)).thenReturn(true);

        boolean result = buyer.removeFromCart(passId);

        Assertions.assertTrue(result);
        Mockito.verify(cart).remove(passId);
    }

    @Test
    void whenRemoveFromCartFails_thenReturnsFalse() {
        Mockito.when(cart.remove(passId)).thenReturn(false);

        boolean result = buyer.removeFromCart(passId);

        Assertions.assertFalse(result);
        Mockito.verify(cart).remove(passId);
    }

    @Test
    void whenClearCart_thenCartClearIsCalled() {
        buyer.clearCart();

        Mockito.verify(cart).clear();
    }

    @Test
    void whenConfirmCartPasses_thenPassesArePurchased() {
        List<Pass> passes = buyer.confirmCartPasses();

        passes.forEach(p -> Assertions.assertTrue(p.isPurchased()));
    }

    @Test
    void whenConfirmCartPasses_thenCartConfirmsPasses() {
        buyer.confirmCartPasses();

        Mockito.verify(cart).linkPassesToBuyer(buyer.getIdul());
    }

    @Test
    void whenConfirmCartPasses_thenCartIsCleared() {
        buyer.confirmCartPasses();

        Mockito.verify(cart).clear();
    }

    @Test
    void whenGetCartBalance_thenCartCalculateAmount() {
        buyer.getCartBalance();

        Mockito.verify(cart).calculateAmount();
    }

    @Test
    void givenBuyerConfirmsCartPasses_whenGetCartPasses_thenReturnsEmptyList() {
        buyer.confirmCartPasses();

        List<Pass> passes = buyer.getCartPasses();

        Assertions.assertTrue(passes.isEmpty());
    }
}
