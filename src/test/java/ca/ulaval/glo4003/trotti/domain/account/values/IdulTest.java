package ca.ulaval.glo4003.trotti.domain.account.values;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class IdulTest {
    private static final String VALID_IDUL = "CM1B2G45";
    private static final String NULL_IDUL = null;

    @Test
    void givenValidIdul_whenCreate_thenIdulIsCreated() {

        Executable idulCreation = () -> Idul.from(VALID_IDUL);

        Assertions.assertDoesNotThrow(idulCreation);
    }

    @Test
    void givenNullIdul_whenCreate_thenThrowInvalidIdulException() {

        Executable idulCreation = () -> Idul.from(NULL_IDUL);

        Assertions.assertThrows(InvalidParameterException.class, idulCreation);
    }

    @Test
    void givenEmptyIdul_whenCreate_thenThrowInvalidIdulException() {

        Executable idulCreation = () -> Idul.from(StringUtils.EMPTY);

        Assertions.assertThrows(InvalidParameterException.class, idulCreation);
    }

    @Test
    void givenTwoIdulWithSameValue_whenCompare_thenTheyAreEqual() {
        Idul idul1 = Idul.from(VALID_IDUL);
        Idul idul2 = Idul.from(VALID_IDUL);

        Assertions.assertEquals(idul1, idul2);
        Assertions.assertEquals(idul1.hashCode(), idul2.hashCode());
    }
}
