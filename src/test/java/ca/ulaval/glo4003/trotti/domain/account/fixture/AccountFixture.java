package ca.ulaval.glo4003.trotti.domain.account.fixture;

import ca.ulaval.glo4003.trotti.domain.account.*;
import java.time.LocalDate;
import org.mockito.Mockito;

public class AccountFixture {
    public static final String A_NAME = "Camavinga";
    public static final LocalDate A_BIRTHDATE = LocalDate.of(2005, 2, 11);
    public static final Gender A_GENDER = Gender.MALE;
    public static final Idul AN_IDUL = Idul.from("CM1B2G45");
    public static final Email AN_EMAIL = Email.from("ahdhhd@ulaval.ca");
    public static final String VALID_PASSWORD = "StrongPass1!";
    public static final PasswordHasher HASHER = Mockito.mock(PasswordHasher.class);
    public static final Password A_PASSWORD = new Password(VALID_PASSWORD, HASHER);

    public static Account createAccount() {
        return new Account(A_NAME, A_BIRTHDATE, A_GENDER, AN_IDUL, AN_EMAIL, A_PASSWORD);
    }
}
