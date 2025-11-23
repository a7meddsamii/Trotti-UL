package ca.ulaval.glo4003.trotti.billing.domain.payment.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class TransactionIdTest {
    private static final String VALID_VALUE = UUID.randomUUID().toString();
    private static final String INVALID_VALUE = "invalid_uuid";

    @Test
    void givenValidValue_whenFrom_thenDoesNotThrowException() {
        Executable from = () -> TransactionId.from(VALID_VALUE);

        Assertions.assertDoesNotThrow(from);
    }

    @Test
    void givenValidValue_whenFrom_thenReturnsTransactionId() {
        TransactionId transactionId = TransactionId.from(VALID_VALUE);

        Assertions.assertNotNull(transactionId);
    }

    @Test
    void givenInvalidValue_whenFrom_thenThrowsException() {
        Executable from = () -> TransactionId.from(INVALID_VALUE);

        Assertions.assertThrows(InvalidParameterException.class, from);
    }
}
