package ca.ulaval.glo4003.trotti.domain.order.entities.buyer;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import ca.ulaval.glo4003.trotti.order.domain.entities.buyer.Cart;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import ca.ulaval.glo4003.trotti.payment.domain.values.money.Money;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartTest {

    private Pass pass;
    private Pass anotherPass;
    private Cart cart;

    @BeforeEach
    void setup() {
        pass = new PassFixture().build();
        anotherPass = new PassFixture().build();
        cart = new Cart(List.of(pass, anotherPass));
    }

    @Test
    void givenCart_whenGetPasses_thenReturnedListIsUnmodifiable() {
        List<Pass> passList = cart.getPasses();

        Assertions.assertThrows(UnsupportedOperationException.class, passList::removeFirst);
    }

    @Test
    void givenCart_whenAddPass_thenPassIsAdded() {
        Pass pass = new PassFixture().build();

        boolean added = cart.add(pass);

        Assertions.assertTrue(added);
        Assertions.assertTrue(cart.getPasses().contains(pass));
    }

    @Test
    void givenCartWithPass_whenRemoveById_thenPassIsRemoved() {
        boolean removed = cart.remove(pass.getId());

        Assertions.assertTrue(removed);
        Assertions.assertFalse(cart.getPasses().contains(pass));
    }

    @Test
    void givenCart_whenClear_thenListIsEmpty() {
        cart.clear();

        Assertions.assertTrue(cart.getPasses().isEmpty());
    }

    @Test
    void givenCartWithPasses_whenCalculateAmount_thenReturnTotal() {
        Money actualTotal = cart.calculateAmount();

        Money expectedTotal = pass.calculateAmount().plus(pass.calculateAmount());
        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void givenCartWithPasses_whenLinkPassesToBuyer_thenReturnLinkedPasses() {
        Idul idul = pass.getBuyerIdul();
        List<Pass> linkedPasses = cart.linkPassesToBuyer(idul);

        Assertions.assertEquals(cart.getPasses().size(), linkedPasses.size());
        Assertions.assertTrue(
                linkedPasses.stream().allMatch(pass -> pass.getBuyerIdul().equals(idul)));
    }
}
