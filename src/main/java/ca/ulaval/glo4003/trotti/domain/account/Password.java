package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class Password {
    private final PasswordHasher hasher;
    private final String hashedValue;

    public static Password fromHashed(String hashedPassword, PasswordHasher hasher) {
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

    private void validate(String hashedPassword) {
        if (StringUtils.isBlank(hashedPassword)) {
            throw new InvalidParameterException("Password cannot be null or empty.");
        }
    }
    private Password(String hashedValue, PasswordHasher hasher) {
        validate(hashedValue);
        this.hasher = hasher;
        this.hashedValue = hashedValue;
    }
}