package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.payment.values.Money;
import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CartTest {
    @Test
    void givenCart_whenGetList_thenReturnedListIsUnmodifiable() {
        Cart cart = new Cart();
        Pass pass = new PassFixture().build();
        cart.add(pass);
        cart.add(pass);

        List<Pass> passList = cart.getList();

        Assertions.assertThrows(UnsupportedOperationException.class, passList::removeFirst);
    }

    @Test
    void givenCart_whenAddPass_thenPassIsAdded() {
        Cart cart = new Cart();
        Pass pass = new PassFixture().build();

        boolean added = cart.add(pass);

        Assertions.assertTrue(added);
        Assertions.assertTrue(cart.getList().contains(pass));
    }

    @Test
    void givenCartWithPass_whenRemoveById_thenPassIsRemoved() {
        Cart cart = new Cart();
        Pass pass = new PassFixture().build();
        cart.add(pass);

        boolean removed = cart.remove(pass.getId());

        Assertions.assertTrue(removed);
        Assertions.assertFalse(cart.getList().contains(pass));
    }

    @Test
    void givenCart_whenClear_thenListIsEmpty() {
        Cart cart = new Cart();
        cart.add(new PassFixture().build());
        cart.add(new PassFixture().build());

        cart.clear();

        Assertions.assertTrue(cart.getList().isEmpty());
    }

    @Test
    void givenCartWithPasses_whenCalculateAmount_thenReturnTotal() {
        Cart cart = new Cart();
        Pass pass1 = new PassFixture().build();
        Pass pass2 = new PassFixture().build();
        cart.add(pass1);
        cart.add(pass2);

        Money actualTotal = cart.calculateAmount();

        Money expectedTotal = pass1.calculateAmount().plus(pass2.calculateAmount());
        Assertions.assertEquals(expectedTotal, actualTotal);
    }
}
