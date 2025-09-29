package ca.ulaval.glo4003.trotti.fixtures;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Gender;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.account.values.Password;
import java.time.LocalDate;
import org.mockito.Mockito;

public class AccountFixture {
    public static final String A_NAME = "Camavinga";
    public static final LocalDate A_BIRTHDATE = LocalDate.of(2005, 2, 11);
    public static final Gender A_GENDER = Gender.MALE;
    public static final Idul AN_IDUL = Idul.from("CM1B2G45");
    public static final Email AN_EMAIL = Email.from("camavinga.123@ulaval.ca");
    public static final String A_RAW_PASSWORD = "StrongPass1!";

    public static final PasswordHasher A_HASHER = Mockito.mock(PasswordHasher.class);
    public static final Password A_PASSWORD = new Password(A_RAW_PASSWORD, A_HASHER);

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

    public Account build() {
        return new Account(name, birthDate, gender, idul, email, password);
    }
}
