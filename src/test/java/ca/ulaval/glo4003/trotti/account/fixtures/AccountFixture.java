package ca.ulaval.glo4003.trotti.account.fixtures;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Gender;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.domain.values.Password;
import java.time.LocalDate;
import org.mockito.Mockito;

public class AccountFixture {
    public static final String A_NAME = "Camavinga";
    public static final String AN_IDUL_STRING = "JD12345";
    public static final String A_GENDER_STRING = "MALE";
    public static final String A_RAW_PASSWORD = "StrongPass1!";
    public static final String A_TOKEN_VALUE = "jwt-token";
    public static final String AN_EMAIL_STRING = "john.doe@ulaval.ca";
    public static final String A_STRING_BIRTHDATE = "2000-01-01";
    public static final LocalDate A_BIRTHDATE = LocalDate.of(2000, 1, 1);

    public static final Gender A_GENDER = Gender.fromString(A_GENDER_STRING);
    public static final Idul AN_IDUL = Idul.from(AN_IDUL_STRING);
    public static final Email AN_EMAIL = Email.from(AN_EMAIL_STRING);
    public static final AuthenticationToken AN_AUTH_TOKEN = AuthenticationToken.from(A_TOKEN_VALUE);

    public static final PasswordHasher A_HASHER = Mockito.mock(PasswordHasher.class);
    public static final Password A_PASSWORD = Password.fromHashed(A_RAW_PASSWORD, A_HASHER);

    private String name = A_NAME;
    private LocalDate birthDate = A_BIRTHDATE;
    private Gender gender = A_GENDER;
    private Idul idul = AN_IDUL;
    private Email email = AN_EMAIL;
    private Password password = A_PASSWORD;

    public AccountFixture withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public AccountFixture withIdul(Idul idul) {
        this.idul = idul;
        return this;
    }

    public Account build() {
        return new Account(name, birthDate, gender, idul, email, password);
    }
}
