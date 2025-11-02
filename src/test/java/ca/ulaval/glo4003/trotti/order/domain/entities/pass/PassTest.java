package ca.ulaval.glo4003.trotti.order.domain.entities.pass;

import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.order.fixtures.PassFixture;
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
    void givenPassNotAssociatedToBuyer_whenLinkToBuyer_thenReturnsNewPassWithBuyerIdul() {
        Pass pass = new PassFixture().withIdul(null).build();

        Pass linkedToBuyerPass = pass.linkToBuyer(AN_IDUL);

        Assertions.assertNotEquals(pass, linkedToBuyerPass);
        Assertions.assertEquals(AN_IDUL, linkedToBuyerPass.getBuyerIdul());
    }

    @Test
    void givenPassAssociatedToBuyer_whenLinkToBuyer_thenPassIsNotChanged() {
        Pass pass = new PassFixture().build();
        pass.linkToBuyer(AN_IDUL);

        Pass linkedToBuyerPass = pass.linkToBuyer(ANOTHER_IDUL);

        Assertions.assertEquals(pass, linkedToBuyerPass);
    }

    @Test
    void givenPassNotAssociatedToBuyer_whenIsPurchased_thenReturnsFalse() {
        Pass pass = new PassFixture().withIdul(null).build();

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
