package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.util.Objects;
import org.apache.commons.validator.routines.RegexValidator;

public class Password {

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{10,}$";
    private static final RegexValidator REGEX_VALIDATOR = new RegexValidator(PASSWORD_PATTERN);

    private final PasswordHasher hasher;
    private final String value;

    public Password(String value, PasswordHasher hasher) {
        validate(value);
        this.hasher = hasher;
        this.value = hasher.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

    private void validate(String password) {
        if (!REGEX_VALIDATOR.isValid(password)) {
            throw new InvalidParameterException(
                    "Invalid password: it must contain at least 10 characters, one uppercase letter, one digit, and one special character.");
        }
    }
}
