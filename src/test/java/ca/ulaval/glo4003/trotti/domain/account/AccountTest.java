package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AccountTest {

    private static final LocalDate A_GIVEN_BIRTHDATE = LocalDate.of(2001, 02, 10);
    private static final int EXPECTED_AGE = 24;
    private static final String VALID_RAW_PASSWORD = "ValidPass123!";
    private static final String INVALID_RAW_PASSWORD = "WrongPassword!";

    private Password mockPassword;
    private Account account;

    @BeforeEach
    void setup() {
        mockPassword = Mockito.mock(Password.class);
        account = createAccountWithMockPassword();
    }

    @Test
    void givenBirthDate_whenGetAge_thenReturnCorrectAge() {
        Account account = new AccountFixture().withBirthDate(A_GIVEN_BIRTHDATE).build();

        Assertions.assertEquals(EXPECTED_AGE, account.getAge());
    }

    @Test
    void givenMatchingPassword_whenVerifyPassword_thenNoExceptionThrown() {
        Mockito.when(mockPassword.matches(VALID_RAW_PASSWORD)).thenReturn(true);

        Executable authenticate = () -> account.verifyPassword(VALID_RAW_PASSWORD);

        Assertions.assertDoesNotThrow(authenticate);
    }

    @Test
    void givenNonMatchingPassword_whenVerifyPassword_thenThrowAuthenticationException() {

        Mockito.when(mockPassword.matches(INVALID_RAW_PASSWORD)).thenReturn(false);

        Executable authenticate = () -> account.verifyPassword(INVALID_RAW_PASSWORD);

        Assertions.assertThrows(AuthenticationException.class, authenticate);
    }

    @Test
    void givenPassword_whenVerifyPasswordMatches_thenPasswordMatchIsCalled() {
        Mockito.when(mockPassword.matches(VALID_RAW_PASSWORD)).thenReturn(true);

        account.verifyPassword(VALID_RAW_PASSWORD);

        Mockito.verify(mockPassword).matches(VALID_RAW_PASSWORD);
    }

    private Account createAccountWithMockPassword() {
        return new Account(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER, AccountFixture.AN_IDUL, AccountFixture.AN_EMAIL,
                mockPassword);
    }
}
