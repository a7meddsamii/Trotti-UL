package ca.ulaval.glo4003.trotti.account.domain.factories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.time.Clock;
import java.time.LocalDate;

public class AccountFactory {
    private static final int MINIMUM_AGE_YEARS = 16;

    private final Clock clock;

    public AccountFactory(Clock clock) {
        this.clock = clock;
    }

    private Account create(String name, LocalDate birthDate, Gender gender, Idul idul, Email email,
            Password password, Role role) {
        validateBirthDate(birthDate);
        return new Account(name, birthDate, gender, idul, email, password, role);
    }

    public Account createUser(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password) {
        return create(name, birthDate, gender, idul, email, password, Role.USER);
    }

    public Account createEmployee(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password) {
        return create(name, birthDate, gender, idul, email, password, Role.EMPLOYEE);
    }

    public Account createTechnician(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password) {
        return create(name, birthDate, gender, idul, email, password, Role.TECHNICIAN);
    }

    private void validateBirthDate(LocalDate birthDate) {
        LocalDate today = LocalDate.now(clock);
        LocalDate minimumValidBirthDate = today.minusYears(MINIMUM_AGE_YEARS);
        if (birthDate.isAfter(minimumValidBirthDate)) {
            throw new InvalidParameterException(
                    "User must be at least " + MINIMUM_AGE_YEARS + " years old.");
        }
    }

}
