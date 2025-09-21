package ca.ulaval.glo4003.trotti.domain.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountFactoryTest {
    private static final String A_NAME = "Camavinga";
    private static final LocalDate A_BIRTHDATE = LocalDate.of(2005, 2, 11);
    private static final Gender A_GENDER = Gender.MALE;
    private static final Idul AN_IDUL = Idul.from("CM1B2G45");
    private static final Email AN_EMAIL = new Email("ahdhhd@ulaval.ca");
    private static final String VALID_PASSWORD = "StrongPass1!";

    Password A_Password = new Password(VALID_PASSWORD);

    private AccountFactory factory;

    @BeforeEach
    void setUp() {
        factory = new AccountFactory();
    }

    @Test
    void givenValidParameters_whenCreateAccount_thenReturnAccountWithExpectedValues() {
        Account account =
                factory.create(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL, AN_EMAIL, A_Password);

        assertNotNull(account);
        assertEquals(A_NAME, account.getName());
        assertEquals(A_BIRTHDATE, account.getBirthDate());
        assertEquals(A_GENDER, account.getGender());
        assertEquals(AN_IDUL, account.getIdul());
        assertEquals(AN_EMAIL, account.getEmail());
        assertEquals(A_Password, account.getHashedPassword());
    }
}
