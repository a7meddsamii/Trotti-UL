package ca.ulaval.glo4003.ws.infrastructure.auth.argon;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.trotti.infrastructure.auth.argon.ArgonHasherConfig;
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
    ArgonHasherConfig config = new ArgonHasherConfig(MEMORY_COST, ITERATIONS, THREADS);
    argonHasher = new ArgonPasswordHasherPortAdapter(config);
  }

  @Test
  void givenPassword_whenHash_thenReturnsPasswordHashedArgon2id() {
    char[] input = PASSWORD.toCharArray();

    String passwordHashed = argonHasher.hash(input);

    assertNotNull(passwordHashed);
    assertTrue(
      passwordHashed.startsWith("$argon2id$"),
      "hash must start with $argon2id$"
    );
  }

  @Test
  void givenCorrectPasswordAndStoredHash_whenVerify_thenReturnsTrue() {
    String passwordHashed = argonHasher.hash(PASSWORD.toCharArray());

    boolean ok = argonHasher.verify(PASSWORD.toCharArray(), passwordHashed);

    assertTrue(ok);
  }

  @Test
  void givenSamePassword_whenHashedTwice_thenHashesDifferBecauseOfRandomSalt() {
    char[] input = PASSWORD.toCharArray();

    String passwordHash1 = argonHasher.hash(input);
    String passwordHash2 = argonHasher.hash(input);

    assertNotEquals(
      passwordHash1,
      passwordHash2,
      "two hashes of the same password hast to be different"
    );
  }

  @Test
  void givenUnicodePassword_whenHashAndVerify_thenWorks() {
    String unicode = NON_ASCII_PASSWORD;
    String passwordHash = argonHasher.hash(unicode.toCharArray());

    assertTrue(argonHasher.verify(unicode.toCharArray(), passwordHash));
    assertFalse(argonHasher.verify("unicode?".toCharArray(), passwordHash));
  }

  @Test
  void originalCharArray_isNotModifiedByHashOrVerify() {
    char[] original = PASSWORD.toCharArray();
    char[] snapshot = PASSWORD.toCharArray();

    String storedHash = argonHasher.hash(original);
    boolean ok = argonHasher.verify(original, storedHash);

    assertArrayEquals(snapshot, original, "input char[] must not change after hashing");
    assertTrue(ok);
  }

  @Test
  void givenNullOrEmptyPassword_whenHash_thenThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> argonHasher.hash(null));
    assertThrows(IllegalArgumentException.class, () -> argonHasher.hash(new char[0]));
  }

  @Test
  void givenNullOrBlankStoredHash_whenVerify_thenReturnsFalse() {
    assertFalse(argonHasher.verify(PASSWORD.toCharArray(), null));
    assertFalse(argonHasher.verify(PASSWORD.toCharArray(), ""));
    assertFalse(argonHasher.verify(PASSWORD.toCharArray(), "   "));
  }

  @Test
  void givenWrongPassword_whenVerify_thenReturnsFalse() {
    String storedHash = argonHasher.hash(PASSWORD.toCharArray());

    boolean ok = argonHasher.verify(WRONG_PASSWORD.toCharArray(), storedHash);

    assertFalse(ok);
  }
}
