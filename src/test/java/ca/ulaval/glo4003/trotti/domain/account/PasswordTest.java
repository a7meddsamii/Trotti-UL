package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PasswordTest {
    private static final String HASHED_PASSWORD = "hashed-password";
    private static final String DIFFERENT_HASHED_PASSWORD = "different-hashed-password";

    private PasswordHasher hasher;

    @BeforeEach
    void setup() {
        hasher = Mockito.mock(PasswordHasher.class);
    }

    @Test
    void givenTwoPasswordsWithSameValue_whenCompare_thenTheyAreEqual() {
        Password password1 = Password.fromHashed(HASHED_PASSWORD, hasher);
        Password password2 = Password.fromHashed(HASHED_PASSWORD, hasher);

        Assertions.assertEquals(password1, password2);
        Assertions.assertEquals(password1.hashCode(), password2.hashCode());
    }

    @Test
    void givenTwoPasswordsWithDifferentValue_whenCompare_thenTheyAreNotEqual() {
        Password password1 = Password.fromHashed(HASHED_PASSWORD, hasher);
        Password password2 = Password.fromHashed(DIFFERENT_HASHED_PASSWORD, hasher);

        Assertions.assertNotEquals(password1, password2);
    }

    @Test
    void givenMatchingRawPassword_whenMatches_thenReturnTrue() {
        Password password = Password.fromHashed(HASHED_PASSWORD, hasher);
        Mockito.when(hasher.matches(AccountFixture.A_RAW_PASSWORD, HASHED_PASSWORD))
                .thenReturn(true);

        boolean result = password.matches(AccountFixture.A_RAW_PASSWORD);

        Assertions.assertTrue(result);
    }

    @Test
    void givenNonMatchingRawPassword_whenMatches_thenReturnFalse() {
        Password password = Password.fromHashed(HASHED_PASSWORD, hasher);
        Mockito.when(hasher.matches(AccountFixture.A_RAW_PASSWORD, HASHED_PASSWORD))
                .thenReturn(false);

        boolean result = password.matches(AccountFixture.A_RAW_PASSWORD);

        Assertions.assertFalse(result);
    }
}
