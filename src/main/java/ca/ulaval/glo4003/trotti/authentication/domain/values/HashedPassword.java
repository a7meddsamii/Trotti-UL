package ca.ulaval.glo4003.trotti.authentication.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo4003.trotti.commons.domain.service.PasswordHasher;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class HashedPassword {
	
    private final PasswordHasher hasher;
    private final String hashedValue;

    public static HashedPassword fromHashed(String hashedPassword, PasswordHasher hasher) {
        return new HashedPassword(hashedPassword, hasher);
    }

    private HashedPassword(String hashedValue, PasswordHasher hasher) {
        validateNotBlank(hashedValue);
        this.hasher = hasher;
        this.hashedValue = hashedValue;
    }

    public boolean matches(String rawPassword) {
        return hasher.matches(rawPassword, this.hashedValue);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HashedPassword hashedPassword = (HashedPassword) o;
        return hashedValue.equals(hashedPassword.hashedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashedValue);
    }

    private static void validateNotBlank(String hashedPassword) {
        if (StringUtils.isBlank(hashedPassword)) {
            throw new InvalidParameterException("Password cannot be null or empty.");
        }
    }
}
