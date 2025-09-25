package ca.ulaval.glo4003.trotti.application.mapper;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.domain.account.*;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.apache.commons.validator.routines.RegexValidator;

public class AccountMapper {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{10,}$";
    private static final RegexValidator REGEX_VALIDATOR = new RegexValidator(PASSWORD_PATTERN);

    private final AccountFactory accountFactory;
    private final PasswordHasher passwordHasher;

    public AccountMapper(AccountFactory accountFactory, PasswordHasher passwordHasher) {
        this.accountFactory = accountFactory;
        this.passwordHasher = passwordHasher;
    }

    public Account create(CreateAccount request) {
        validate(request.password());
        Email email = Email.from(request.email());
        Password password =
                Password.fromHashed(passwordHasher.hash(request.password()), passwordHasher);
        Gender gender = Gender.fromString(request.gender());
        Idul idul = Idul.from(request.idul());

        return accountFactory.create(request.name(), request.birthDate(), gender, idul, email,
                password);
    }

    private void validate(String password) {
        if (!REGEX_VALIDATOR.isValid(password)) {
            throw new InvalidParameterException(
                    "Invalid password: it must contain at least 10 characters, one uppercase letter, one digit, and one special character.");
        }
    }
}
