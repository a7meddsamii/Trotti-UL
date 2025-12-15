package ca.ulaval.glo4003.trotti.billing.domain.ridepermit.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class RidePermitIdTest {
    private static final String VALID_VALUE = UUID.randomUUID().toString();
    private static final String INVALID_VALUE = "invalid_uuid";

    @Test
    void givenValidValue_whenFrom_thenDoesNotThrowException() {
        Executable from = () -> RidePermitId.from(VALID_VALUE);

        Assertions.assertDoesNotThrow(from);
    }

    @Test
    void givenValidValue_whenFrom_thenReturnsRidePermitId() {
        RidePermitId ridePermitId = RidePermitId.from(VALID_VALUE);

        Assertions.assertNotNull(ridePermitId);
    }

    @Test
    void givenInvalidValue_whenFrom_thenThrowsException() {
        Executable from = () -> RidePermitId.from(INVALID_VALUE);

        Assertions.assertThrows(InvalidParameterException.class, from);
    }
}
