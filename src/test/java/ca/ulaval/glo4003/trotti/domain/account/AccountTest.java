package ca.ulaval.glo4003.trotti.domain.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {
  private static final String A_NAME = "Camavinga";
  private static final LocalDate A_BIRTHDATE = LocalDate.of(2005, 2, 11);
  private static final Gender A_GENDER = Gender.MALE;
  private static final String AN_IDUL = "CM1B2G45";
  private static final Email AN_EMAIL = new Email("ahdhhd@ulaval.ca");
  private static final String A_HASHED_PASSWORD = "626svgftysgat66162twvy61t2";

  private Account account;

  @BeforeEach
  void setUp() {
    account =
      new Account(
        A_NAME,
        A_BIRTHDATE,
        A_GENDER,
        AN_IDUL,
        AN_EMAIL,
        A_HASHED_PASSWORD
      );
  }

  @Test
  void givenAccount_whenGetters_thenReturnExpectedValues() {
    assertEquals(A_NAME, account.getName());
    assertEquals(A_BIRTHDATE, account.getBirthDate());
    assertEquals(A_GENDER, account.getGender());
    assertEquals(AN_IDUL, account.getIdul());
    assertEquals(AN_EMAIL, account.getEmail());
    assertEquals(A_HASHED_PASSWORD, account.getHashedPassword());
  }

  // TODO (A discuter en equipe)
  @Test
  void givenBirthDate20YearsAgo_whenGetAge_thenReturn20() {
    LocalDate today = LocalDate.now();
    int expectedAge = Period.between(A_BIRTHDATE, today).getYears();

    assertEquals(expectedAge, account.getAge());
  }
}
