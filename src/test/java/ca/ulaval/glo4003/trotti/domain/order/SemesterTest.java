package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.domain.order.values.Semester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class SemesterTest {

    private static final String FALL_SESSION = "Fall";
    private static final String FALL_SESSION_FRENCH = "Automne";
    private static final String WINTER_SESSION_LOWERCASE = "winter";

    @Test
    void givenFallString_whenFromString_thenReturnFallEnum() {
        Semester semester = Semester.fromString(FALL_SESSION);

        Assertions.assertEquals(Semester.FALL, semester);
    }

    @Test
    void givenFrenchFallString_whenFromString_thenReturnFallEnum() {
        Semester semester = Semester.fromString(FALL_SESSION_FRENCH);

        Assertions.assertEquals(Semester.FALL, semester);
    }

    @Test
    void givenWinterStringLowercase_whenFromString_thenReturnWinterEnum() {
        Semester semester = Semester.fromString(WINTER_SESSION_LOWERCASE);

        Assertions.assertEquals(Semester.WINTER, semester);
    }

    @Test
    void givenInvalidString_whenFromString_thenThrowsException() {
        Executable executable = () -> Semester.fromString("Invalid");

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }
}
