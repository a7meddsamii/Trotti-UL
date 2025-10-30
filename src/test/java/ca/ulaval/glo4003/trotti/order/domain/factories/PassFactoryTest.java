package ca.ulaval.glo4003.trotti.order.domain.factories;

import ca.ulaval.glo4003.trotti.order.fixtures.PassFixture;
import ca.ulaval.glo4003.trotti.order.domain.entities.pass.Pass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PassFactoryTest {
    private final PassFactory factory = new PassFactory();

    @Test
    void givenValidParams_whenCreate_thenReturnPass() {
        Pass pass = factory.create(PassFixture.A_MAXIMUM_TRAVELING_TIME, PassFixture.A_SESSION,
                PassFixture.A_BILLING_FREQUENCY);

        Assertions.assertEquals(PassFixture.A_MAXIMUM_TRAVELING_TIME,
                pass.getMaximumTravelingTime());
        Assertions.assertEquals(PassFixture.A_SESSION, pass.getSession());
        Assertions.assertEquals(PassFixture.A_BILLING_FREQUENCY, pass.getBillingFrequency());
        Assertions.assertNotNull(pass.getId());
    }
}
