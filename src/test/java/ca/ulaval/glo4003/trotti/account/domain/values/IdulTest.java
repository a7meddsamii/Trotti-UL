package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class IdulTest {
    private static final String VALID_IDUL = "CM1B2G45";

    @Test
    void givenValidIdul_whenCreate_thenIdulIsCreated() {
        // when
        Executable idulCreation = () -> Idul.from(VALID_IDUL);

        // then
        Assertions.assertDoesNotThrow(idulCreation);
    }

    @Test
    void givenNullIdul_whenCreate_thenThrowsInvalidParameterException() {
        // when
        Executable idulCreation = () -> Idul.from(null);

        // then
        Assertions.assertThrows(InvalidParameterException.class, idulCreation);
    }

    @Test
    void givenEmptyIdul_whenCreate_thenThrowsInvalidParameterException() {
        // when
        Executable idulCreation = () -> Idul.from(StringUtils.EMPTY);

        // then
        Assertions.assertThrows(InvalidParameterException.class, idulCreation);
    }

    @Test
    void givenTwoIdulWithSameValue_whenCompare_thenTheyAreEqual() {
        // given
        Idul idul1 = Idul.from(VALID_IDUL);
        Idul idul2 = Idul.from(VALID_IDUL);

        // then
        Assertions.assertEquals(idul1, idul2);
        Assertions.assertEquals(idul1.hashCode(), idul2.hashCode());
    }
}
