package ca.ulaval.glo4003.trotti.domain.pass;

import static ca.ulaval.glo4003.trotti.domain.pass.PassTest.*;

import ca.ulaval.glo4003.trotti.domain.account.Idul;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class OrderTest {
    private static final Idul VALID_IDUL = Idul.from("CM1B2G45");

    @Test
    void givenValidParameters_whenCreation_thenObjectIsCreated() {
        Executable creation = () -> new Order(VALID_IDUL, List.of(
                new Pass(VALID_MAXIMUM_TRAVELING_TIME, VALID_SESSION, VALID_BILLING_FREQUENCY)));

        Assertions.assertDoesNotThrow(creation);
    }
}
