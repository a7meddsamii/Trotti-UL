package ca.ulaval.glo4003.ws.infrastructure.auth.argon;

import org.junit.jupiter.api.Assertions;

import ca.ulaval.glo4003.trotti.infrastructure.auth.argon.ArgonPasswordHasherPortAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArgonPasswordHasherPortAdapterTest {

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
        char[] plainPassword = PASSWORD.toCharArray();

        String passwordHashed = argonHasher.hash(plainPassword);

        Assertions.assertNotNull(passwordHashed);
        Assertions.assertTrue(passwordHashed.startsWith("$argon2id$"), "hash must start with $argon2id$");
    }

    @Test
    void givenCorrectPasswordAndStoredHash_whenVerify_thenReturnsTrue() {
        String passwordHashed = argonHasher.hash(PASSWORD.toCharArray());

        boolean ok = argonHasher.matches(PASSWORD.toCharArray(), passwordHashed);

        Assertions.assertTrue(ok);
    }

    @Test
    void givenSamePassword_whenHashedTwice_thenHashesDifferBecauseOfRandomSalt() {
        char[] input = PASSWORD.toCharArray();

        String passwordHash1 = argonHasher.hash(input);
        String passwordHash2 = argonHasher.hash(input);

        Assertions.assertNotEquals(passwordHash1, passwordHash2);
    }

    @Test
    void givenUnicodePassword_whenHashAndVerify_thenWorks() {
        String unicode = NON_ASCII_PASSWORD;
        String passwordHash = argonHasher.hash(unicode.toCharArray());

        Assertions.assertTrue(argonHasher.matches(unicode.toCharArray(), passwordHash));
        Assertions.assertFalse(argonHasher.matches("unicode?".toCharArray(), passwordHash));
    }

    @Test
    void originalCharArray_isNotModifiedByHashOrVerify() {
        char[] original = PASSWORD.toCharArray();
        char[] snapshot = PASSWORD.toCharArray();

        String storedHash = argonHasher.hash(original);
        boolean ok = argonHasher.matches(original, storedHash);

        Assertions.assertArrayEquals(snapshot, original);
        Assertions.assertTrue(ok);
    }

    @Test
    void givenWrongPassword_whenVerify_thenReturnsFalse() {
        String storedHash = argonHasher.hash(PASSWORD.toCharArray());

        boolean ok = argonHasher.matches(WRONG_PASSWORD.toCharArray(), storedHash);

        Assertions.assertFalse(ok);
    }
}
