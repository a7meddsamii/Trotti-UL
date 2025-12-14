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
    public static final String NAME = "Camavinga";
    public static final String IDUL_STRING = "JD12345";
    public static final String GENDER_STRING = "MALE";
    public static final String RAW_PASSWORD = "StrongPass1!";
    public static final String EMAIL_STRING = "john.doe@ulaval.ca";
    public static final String STRING_BIRTHDATE = "2000-01-01";
    public static final LocalDate BIRTHDATE = LocalDate.of(2000, 1, 1);
    public static final String ROLE_STRING = "STUDENT";

    public static final Gender GENDER = Gender.fromString(GENDER_STRING);
    public static final Idul IDUL = Idul.from(IDUL_STRING);
    public static final Email EMAIL = Email.from(EMAIL_STRING);

    public static final PasswordHasher HASHER = Mockito.mock(PasswordHasher.class);

    public static final Role ROLE = Role.fromString(ROLE_STRING);
    public static final Set<Permission> SET_OF_PERMISSION = Set.of();
    public static final Set<Advantage> SET_OF_ADVANTAGES = Set.of();

    private String name = NAME;
    private LocalDate birthDate = BIRTHDATE;
    private Gender gender = GENDER;
    private Idul idul = IDUL;
    private Email email = EMAIL;
    private Role role = ROLE;
    private Set<Permission> permissions = SET_OF_PERMISSION;
    private Set<Advantage> advantages = SET_OF_ADVANTAGES;

    public AccountFixture withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public AccountFixture withIdul(Idul idul) {
        this.idul = idul;
        return this;
    }

    public Account build() {
        return new Account(name, birthDate, gender, idul, email, role, permissions, advantages);
    }
}
