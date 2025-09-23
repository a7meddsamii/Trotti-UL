package ca.ulaval.glo4003.ws.infrastructure.auth.argon;

import ca.ulaval.glo4003.trotti.infrastructure.auth.argon.Argon2PasswordHasherPortAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArgonPasswordHasherAdapterTest {

    private static final String PLAIN_PASSWORD = "SecretTest1!";
    private static final String WRONG_PASSWORD = "SecretTest2!";
    private static final String NON_ASCII_PASSWORD = "UniqueCaractère!";

    private static final int MEMORY_COST = 64 * 1024;
    private static final int ITERATIONS = 3;
    private static final int THREADS = 2;

    private Argon2PasswordHasherPortAdapter argonHasher;

    @BeforeEach
    void setUp() {
        argonHasher = new Argon2PasswordHasherPortAdapter(MEMORY_COST, ITERATIONS, THREADS);
    }

    @Test
    void givenPassword_whenHash_thenReturnsPasswordHashedArgon2id() {
        String hashedPassword = argonHasher.hash(PLAIN_PASSWORD);

        Assertions.assertNotEquals(PLAIN_PASSWORD, hashedPassword);
    }

    @Test
    void givenCorrectPasswordAndStoredHash_whenVerify_thenReturnsTrue() {
        String hashedPassword = argonHasher.hash(PLAIN_PASSWORD);

        boolean ok = argonHasher.matches(PLAIN_PASSWORD, hashedPassword);

        Assertions.assertTrue(ok);
    }

    @Test
    void givenSamePassword_whenHashedTwice_thenHashesDifferBecauseOfRandomSalt() {
        String hashedPassword1 = argonHasher.hash(PLAIN_PASSWORD);
        String hashedPassword2 = argonHasher.hash(PLAIN_PASSWORD);

        Assertions.assertNotEquals(hashedPassword1, hashedPassword2);
    }

    @Test
    void givenUnicodePassword_whenHashAndVerify_thenWorks() {
        String hashedPassword = argonHasher.hash(NON_ASCII_PASSWORD);

        Assertions.assertTrue(argonHasher.matches(NON_ASCII_PASSWORD, hashedPassword));
        Assertions.assertFalse(argonHasher.matches("unicode?", hashedPassword));
    }


    @Test
    void givenWrongPassword_whenVerify_thenReturnsFalse() {
        String storedHash = argonHasher.hash(PLAIN_PASSWORD);

        boolean ok = argonHasher.matches(WRONG_PASSWORD, storedHash);

        Assertions.assertFalse(ok);
    }
}
