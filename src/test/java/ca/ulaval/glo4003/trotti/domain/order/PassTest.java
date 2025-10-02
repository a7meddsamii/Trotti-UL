package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.fixtures.PassFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PassTest {

    private static final Idul AN_IDUL = Idul.from("A1234567");
    private static final Idul ANOTHER_IDUL = Idul.from("B7654321");

    @Test
    void givenPass_whenCalculateAmount_thenReturnMoney() {
        Pass pass = new PassFixture().build();

        Assertions.assertNotNull(pass.calculateAmount());
    }

    @Test
    void givenPassNotAssociatedToBuyer_whenLinkToBuyer_thenReturnsTrue() {
        Pass pass = new PassFixture().build();

        boolean isLinkedToBuyer = pass.linkToBuyer(AN_IDUL);

        Assertions.assertTrue(isLinkedToBuyer);
    }

    @Test
    void givenPassAssociatedToBuyer_whenLinkToBuyer_thenReturnsFalse() {
        Pass pass = new PassFixture().build();
        pass.linkToBuyer(AN_IDUL);

        boolean isLinkedToNewBuyer = pass.linkToBuyer(ANOTHER_IDUL);

        Assertions.assertFalse(isLinkedToNewBuyer);
    }

    @Test
    void givenPassNotAssociatedToBuyer_whenIsPurchased_thenReturnsFalse() {
        Pass pass = new PassFixture().build();

        boolean isPurchased = pass.isPurchased();

        Assertions.assertFalse(isPurchased);
    }

    @Test
    void givenPassAssociatedToBuyer_whenIsPurchased_thenReturnsTrue() {
        Pass pass = new PassFixture().build();
        pass.linkToBuyer(AN_IDUL);

        boolean isPurchased = pass.isPurchased();

        Assertions.assertTrue(isPurchased);
    }

    @Test
    void givenPass_whenCalculateAmount_thenMaximumDailyTravelTimeCalculatesAmount() {
        Pass pass = new PassFixture().build();

        Assertions.assertEquals(pass.calculateAmount(),
                pass.getMaximumTravelingTime().calculateAmount());
    }
}
