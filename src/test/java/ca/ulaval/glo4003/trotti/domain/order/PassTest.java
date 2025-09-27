package ca.ulaval.glo4003.trotti.domain.order;

import static ca.ulaval.glo4003.trotti.domain.order.SessionTest.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class PassTest {
    public static final MaximumTravelingTime VALID_MAXIMUM_TRAVELING_TIME =
            MaximumTravelingTime.from(20);
    public static final Session VALID_SESSION =
            new Session(VALID_SESSION_A, VALID_START_DATE, VALID_END_DATE);
    public static final BillingFrequency VALID_BILLING_FREQUENCY = BillingFrequency.PER_TRIP;

    @Test
    public void givenValidParameters_whenCreation_thenObjectIsCreated() {
        Executable creation = () -> new Pass(VALID_MAXIMUM_TRAVELING_TIME, VALID_SESSION,
                VALID_BILLING_FREQUENCY);

        Assertions.assertDoesNotThrow(creation);
    }
}
