package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.values.Gender;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class GenderTest {
    private static final String MALE_STRING = "MAlE";
    private static final String MALE_SHORTCUT = "m";
    private static final String FEMALE_STRING = "female";
    private static final String FEMALE_SHORTCUT = "f";
    private static final String NON_BINARY_STRING = "non-binary";
    private static final String NON_BINARY_ALT = "non_binary";
    private static final String NON_BINARY_SHORTCUT = "nb";
    private static final String UNSPECIFIED_STRING = "unspecified";
    private static final String UNSPECIFIED_SHORTCUT = "u";
    private static final String INVALID_STRING = "other";

    private static final String MALE_LABEL = "Male";
    private static final String FEMALE_LABEL = "Female";
    private static final String NON_BINARY_LABEL = "Non-binary";
    private static final String UNSPECIFIED_LABEL = "Unspecified";

    @Test
    void givenMaleString_whenFromString_thenReturnMaleEnum() {

        Assertions.assertEquals(Gender.MALE, Gender.fromString(MALE_STRING));
        Assertions.assertEquals(Gender.MALE, Gender.fromString(MALE_SHORTCUT));
    }

    @Test
    void givenFemaleString_whenFromString_thenReturnFemaleEnum() {

        Assertions.assertEquals(Gender.FEMALE, Gender.fromString(FEMALE_STRING));
        Assertions.assertEquals(Gender.FEMALE, Gender.fromString(FEMALE_SHORTCUT));
    }

    @Test
    void givenNonBinaryString_whenFromString_thenReturnNonBinaryEnum() {

        Assertions.assertEquals(Gender.NON_BINARY, Gender.fromString(NON_BINARY_STRING));
        Assertions.assertEquals(Gender.NON_BINARY, Gender.fromString(NON_BINARY_ALT));
        Assertions.assertEquals(Gender.NON_BINARY, Gender.fromString(NON_BINARY_SHORTCUT));
    }

    @Test
    void givenUnspecifiedString_whenFromString_thenReturnUnspecifiedEnum() {
        Assertions.assertEquals(Gender.UNSPECIFIED, Gender.fromString(UNSPECIFIED_STRING));
        Assertions.assertEquals(Gender.UNSPECIFIED, Gender.fromString(UNSPECIFIED_SHORTCUT));
    }

    @Test
    void givenInvalidString_whenFromString_thenThrowInvalidGenderException() {

        Executable executable = () -> Gender.fromString(INVALID_STRING);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }

    @Test
    void givenEnumConstant_whenToString_thenReturnExpectedLabel() {

        Assertions.assertEquals(MALE_LABEL, Gender.MALE.toString());
        Assertions.assertEquals(FEMALE_LABEL, Gender.FEMALE.toString());
        Assertions.assertEquals(NON_BINARY_LABEL, Gender.NON_BINARY.toString());
        Assertions.assertEquals(UNSPECIFIED_LABEL, Gender.UNSPECIFIED.toString());
    }

    @Test
    void givenInvalidString_whenFromString_thenThrowInvalidGenderExceptionWithAcceptedValuesInMessage() {

        Executable executable = () -> Gender.fromString(INVALID_STRING);

        InvalidParameterException exception =
                Assertions.assertThrows(InvalidParameterException.class, executable);
        Assertions.assertTrue(exception.getMessage().contains(Gender.MALE.toString()));
        Assertions.assertTrue(exception.getMessage().contains(Gender.FEMALE.toString()));
        Assertions.assertTrue(exception.getMessage().contains(Gender.NON_BINARY.toString()));
        Assertions.assertTrue(exception.getMessage().contains(Gender.UNSPECIFIED.toString()));
    }

}
