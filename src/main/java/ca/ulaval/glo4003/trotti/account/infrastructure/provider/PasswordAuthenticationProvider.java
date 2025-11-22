package ca.ulaval.glo4003.trotti.account.infrastructure.provider;

import ca.ulaval.glo4003.trotti.account.application.AuthenticationProvider;
import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.validator.routines.RegexValidator;

public class PasswordAuthenticationProvider implements AuthenticationProvider {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{10,}$";
    private static final RegexValidator REGEX_VALIDATOR = new RegexValidator(PASSWORD_PATTERN);
    private final Map<Email, String> usersCredentials = new HashMap<>();
    private final PasswordHasher passwordHasher;

    public PasswordAuthenticationProvider(PasswordHasher passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Email verify(LoginDto loginInfo) {
        String hashedPassword = usersCredentials.get(loginInfo.email());
        if(!this.passwordHasher.matches(loginInfo.password(), hashedPassword)){
            throw new AuthenticationException("Invalid email or password");
        }

        return loginInfo.email();
    }

    @Override
    public AccountDto register(RegistrationDto registrationInfo) {
        validatePassword(registrationInfo.password());
        String hashedPassword = passwordHasher.hash(registrationInfo.password());
        usersCredentials.put(registrationInfo.email(), hashedPassword);
        return new AccountDto(registrationInfo.name(), registrationInfo.birthDate(),
                registrationInfo.gender(), registrationInfo.idul(), registrationInfo.email(),
                registrationInfo.role());
    }

    private void validatePassword(String plainPassword) {
        if (!REGEX_VALIDATOR.isValid(plainPassword)) {
            throw new InvalidParameterException(
                    "Invalid password: it must contain at least 10 characters, one uppercase letter, one digit, and one special character.");
        }
    }
}
