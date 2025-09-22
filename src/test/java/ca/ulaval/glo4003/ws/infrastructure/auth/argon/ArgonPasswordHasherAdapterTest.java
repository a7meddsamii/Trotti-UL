package ca.ulaval.glo4003.ws.infrastructure.auth.argon;

import ca.ulaval.glo4003.trotti.infrastructure.auth.argon.ArgonPasswordHasherPortAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArgonPasswordHasherAdapterTest {

    private static final String PASSWORD = "SecretTest1!";
    private static final String WRONG_PASSWORD = "SecretTest2!";
    private static final String NON_ASCII_PASSWORD = "UniqueCaractère!";

    private static final int MEMORY_COST = 64 * 1024;
    private static final int ITERATIONS = 3;
    private static final int THREADS = 2;

    private ArgonPasswordHasherPortAdapter argonHasher;

    @BeforeEach
    void setUp() {
        argonHasher = new ArgonPasswordHasherPortAdapter(MEMORY_COST, ITERATIONS, THREADS);
    }

    @Test
    void givenPassword_whenHash_thenReturnsPasswordHashedArgon2id() {
        String passwordHashed = argonHasher.hash(PASSWORD);

        Assertions.assertNotNull(passwordHashed);
        Assertions.assertTrue(passwordHashed.startsWith("$argon2id$"),
                "hash must start with $argon2id$");
    }

    @Test
    void givenCorrectPasswordAndStoredHash_whenVerify_thenReturnsTrue() {
        String passwordHashed = argonHasher.hash(PASSWORD);

        boolean ok = argonHasher.matches(PASSWORD, passwordHashed);

        Assertions.assertTrue(ok);
    }

    @Test
    void givenSamePassword_whenHashedTwice_thenHashesDifferBecauseOfRandomSalt() {
        String passwordHash1 = argonHasher.hash(PASSWORD);
        String passwordHash2 = argonHasher.hash(PASSWORD);

        Assertions.assertNotEquals(passwordHash1, passwordHash2);
    }

    @Test
    void givenUnicodePassword_whenHashAndVerify_thenWorks() {
        String passwordHash = argonHasher.hash(NON_ASCII_PASSWORD);

        Assertions.assertTrue(argonHasher.matches(NON_ASCII_PASSWORD, passwordHash));
        Assertions.assertFalse(argonHasher.matches("unicode?", passwordHash));
    }


    @Test
    void givenWrongPassword_whenVerify_thenReturnsFalse() {
        String storedHash = argonHasher.hash(PASSWORD);

        boolean ok = argonHasher.matches(WRONG_PASSWORD, storedHash);

        Assertions.assertFalse(ok);
    }
}
