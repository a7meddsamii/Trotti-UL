package ca.ulaval.glo4003.trotti.infrastructure.account.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Argon2PasswordHasherAdapterTest {

    private static final String PLAIN_PASSWORD = "SecretTest1!";
    private static final String WRONG_PASSWORD = "SecretTest2!";
    private static final String NON_ASCII_PASSWORD = "UniqueCaractère!";

    private static final int MEMORY_COST = 64 * 1024;
    private static final int ITERATIONS = 3;
    private static final int THREADS = 2;

    private Argon2PasswordHasherAdapter argon2Hasher;

    @BeforeEach
    void setup() {
        argon2Hasher = new Argon2PasswordHasherAdapter(MEMORY_COST, ITERATIONS, THREADS);
    }

    @Test
    void givenPassword_whenHash_thenReturnPasswordHashedArgon2id() {
        String hashedPassword = argon2Hasher.hash(PLAIN_PASSWORD);

        Assertions.assertNotEquals(PLAIN_PASSWORD, hashedPassword);
    }

    @Test
    void givenCorrectPasswordAndStoredHash_whenVerify_thenReturnTrue() {
        String hashedPassword = argon2Hasher.hash(PLAIN_PASSWORD);

        boolean ok = argon2Hasher.matches(PLAIN_PASSWORD, hashedPassword);

        Assertions.assertTrue(ok);
    }

    @Test
    void givenSamePassword_whenHashedTwice_thenHashesDifferBecauseOfRandomSalt() {
        String hashedPassword1 = argon2Hasher.hash(PLAIN_PASSWORD);
        String hashedPassword2 = argon2Hasher.hash(PLAIN_PASSWORD);

        Assertions.assertNotEquals(hashedPassword1, hashedPassword2);
    }

    @Test
    void givenUnicodePassword_whenHashAndVerify_thenWorks() {
        String hashedPassword = argon2Hasher.hash(NON_ASCII_PASSWORD);

        Assertions.assertTrue(argon2Hasher.matches(NON_ASCII_PASSWORD, hashedPassword));
        Assertions.assertFalse(argon2Hasher.matches("unicode?", hashedPassword));
    }

    @Test
    void givenWrongPassword_whenVerify_thenReturnFalse() {
        String storedHash = argon2Hasher.hash(PLAIN_PASSWORD);

        boolean ok = argon2Hasher.matches(WRONG_PASSWORD, storedHash);

        Assertions.assertFalse(ok);
    }
}
