package ca.ulaval.glo4003.trotti.domain.order;

import static ca.ulaval.glo4003.trotti.domain.order.SessionTest.*;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PassTest {
    public static final MaximumDailyTravelTime VALID_MAXIMUM_TRAVELING_TIME =
            MaximumDailyTravelTime.from(Duration.ofMinutes(20));

    public static final Session VALID_SESSION =
            new Session(VALID_SESSION_A, VALID_START_DATE, VALID_END_DATE);

    public static final BillingFrequency VALID_BILLING_FREQUENCY = BillingFrequency.PER_TRIP;

    @Test
    void givenValidParameters_whenCreation_thenObjectIsCreated() {
        Pass pass = new Pass(VALID_MAXIMUM_TRAVELING_TIME, VALID_SESSION, VALID_BILLING_FREQUENCY);

        Assertions.assertNotNull(pass);
    }
}
