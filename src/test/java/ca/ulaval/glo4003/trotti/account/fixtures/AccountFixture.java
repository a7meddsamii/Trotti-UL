package ca.ulaval.glo4003.trotti.account.fixtures;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;
import org.mockito.Mockito;

public class AccountFixture {
    public static final String A_NAME = "Camavinga";
    public static final String AN_IDUL_STRING = "JD12345";
    public static final String A_GENDER_STRING = "MALE";
    public static final String A_RAW_PASSWORD = "StrongPass1!";
    public static final String AN_EMAIL_STRING = "john.doe@ulaval.ca";
    public static final String A_STRING_BIRTHDATE = "2000-01-01";
    public static final LocalDate A_BIRTHDATE = LocalDate.of(2000, 1, 1);
    public static final String A_ROLE_STRING = "STUDENT";

    public static final Gender A_GENDER = Gender.fromString(A_GENDER_STRING);
    public static final Idul AN_IDUL = Idul.from(AN_IDUL_STRING);
    public static final Email AN_EMAIL = Email.from(AN_EMAIL_STRING);

    public static final PasswordHasher A_HASHER = Mockito.mock(PasswordHasher.class);

    public static final Role A_ROLE = Role.fromString(A_ROLE_STRING);
    public static final Set<Permission> A_SET_OF_PERMISSION = Set.of();

    private String name = A_NAME;
    private LocalDate birthDate = A_BIRTHDATE;
    private Gender gender = A_GENDER;
    private Idul idul = AN_IDUL;
    private Email email = AN_EMAIL;
    private Role role = A_ROLE;
    private Set<Permission> permissions = A_SET_OF_PERMISSION;

    public AccountFixture withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public AccountFixture withIdul(Idul idul) {
        this.idul = idul;
        return this;
    }

    public Account build() {
        return new Account(name, birthDate, gender, idul, email, role, permissions);
    }
}
