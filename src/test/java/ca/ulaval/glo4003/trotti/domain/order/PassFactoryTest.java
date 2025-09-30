package ca.ulaval.glo4003.trotti.domain.order;

import static ca.ulaval.glo4003.trotti.fixtures.PassFixture.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PassFactoryTest {
    private final PassFactory factory = new PassFactory();

    @Test
    void givenValidParams_whenCreate_thenReturnPass() {
        Pass pass = factory.create(A_MAXIMUM_TRAVELING_TIME, A_SESSION, A_BILLING_FREQUENCY);

        Assertions.assertEquals(A_MAXIMUM_TRAVELING_TIME, pass.getMaximumTravelingTime());
        Assertions.assertEquals(A_SESSION, pass.getSession());
        Assertions.assertEquals(A_BILLING_FREQUENCY, pass.getBillingFrequency());
        Assertions.assertNotNull(pass.getId());
    }
}
