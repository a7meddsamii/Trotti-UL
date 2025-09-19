package ca.ulaval.glo4003.trotti.domain.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.trotti.domain.account.exception.InvalidGenderException;
import org.junit.jupiter.api.Test;

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
    assertEquals(Gender.MALE, Gender.fromString(MALE_STRING));
    assertEquals(Gender.MALE, Gender.fromString(MALE_SHORTCUT));
  }

  @Test
  void givenFemaleString_whenFromString_thenReturnFemaleEnum() {
    assertEquals(Gender.FEMALE, Gender.fromString(FEMALE_STRING));
    assertEquals(Gender.FEMALE, Gender.fromString(FEMALE_SHORTCUT));
  }

  @Test
  void givenNonBinaryString_whenFromString_thenReturnNonBinaryEnum() {
    assertEquals(Gender.NON_BINARY, Gender.fromString(NON_BINARY_STRING));
    assertEquals(Gender.NON_BINARY, Gender.fromString(NON_BINARY_ALT));
    assertEquals(Gender.NON_BINARY, Gender.fromString(NON_BINARY_SHORTCUT));
  }

  @Test
  void givenUnspecifiedString_whenFromString_thenReturnUnspecifiedEnum() {
    assertEquals(Gender.UNSPECIFIED, Gender.fromString(UNSPECIFIED_STRING));
    assertEquals(Gender.UNSPECIFIED, Gender.fromString(UNSPECIFIED_SHORTCUT));
  }

  @Test
  void givenInvalidString_whenFromString_thenThrowInvalidGenderException() {
    assertThrows(
      InvalidGenderException.class,
      () -> Gender.fromString(INVALID_STRING)
    );
  }

  @Test
  void givenEnumConstant_whenToString_thenReturnExpectedLabel() {
    assertEquals(MALE_LABEL, Gender.MALE.toString());
    assertEquals(FEMALE_LABEL, Gender.FEMALE.toString());
    assertEquals(NON_BINARY_LABEL, Gender.NON_BINARY.toString());
    assertEquals(UNSPECIFIED_LABEL, Gender.UNSPECIFIED.toString());
  }
}
