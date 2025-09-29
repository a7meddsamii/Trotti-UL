package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Gender;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.account.values.Password;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.time.Clock;
import java.time.LocalDate;

public class AccountFactory {

    private final Clock clock;

    public AccountFactory(Clock clock) {
        this.clock = clock;
    }

    public Account create(String name, LocalDate birthDate, Gender gender, Idul idul, Email email,
            Password password) {
        validateBirthDate(birthDate);
        return new Account(name, birthDate, gender, idul, email, password);
    }

    private void validateBirthDate(LocalDate birthDate) {
        LocalDate today = LocalDate.now(clock);
        if (!birthDate.isBefore(today)) {
            throw new InvalidParameterException("Birthdate cannot be today or in the future.");
        }
    }
}
