package ca.ulaval.glo4003.trotti.domain.account.values;

import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.RegexValidator;

public class Password {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{10,}$";
    private static final RegexValidator REGEX_VALIDATOR = new RegexValidator(PASSWORD_PATTERN);

    private final PasswordHasher hasher;
    private final String hashedValue;

    public static Password fromHashed(String hashedPassword, PasswordHasher hasher) {
        return new Password(hashedPassword, hasher);
    }

    private Password(String hashedValue, PasswordHasher hasher) {
        validateNotBlank(hashedValue);
        this.hasher = hasher;
        this.hashedValue = hashedValue;
    }

    public static Password fromPlain(String plainPassword, PasswordHasher hasher) {
        validateNotBlank(plainPassword);
        ensurePasswordMeetsPolicy(plainPassword);
        String hashedPassword = hasher.hash(plainPassword);
        return new Password(hashedPassword, hasher);
    }

    public boolean matches(String rawPassword) {
        return hasher.matches(rawPassword, this.hashedValue);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return hashedValue.equals(password.hashedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashedValue);
    }

    @Override
    public String toString() {
        return hashedValue;
    }

    private static void ensurePasswordMeetsPolicy(String password) {
        if (!REGEX_VALIDATOR.isValid(password)) {
            throw new InvalidParameterException(
                    "Invalid password: it must contain at least 10 characters, one uppercase letter, one digit, and one special character.");
        }
    }

    private static void validateNotBlank(String hashedPassword) {
        if (StringUtils.isBlank(hashedPassword)) {
            throw new InvalidParameterException("Password cannot be null or empty.");
        }
    }
}
