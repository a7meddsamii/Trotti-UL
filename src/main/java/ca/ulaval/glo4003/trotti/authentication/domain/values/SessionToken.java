package ca.ulaval.glo4003.trotti.authentication.domain.values;

import ca.ulaval.glo4003.trotti.authentication.domain.exception.AuthenticationException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class SessionToken {

    private final String value;

    public static SessionToken from(String value) {
        return new SessionToken(value);
    }

    private SessionToken(String value) {
        if (StringUtils.isBlank(value)) {
            throw new AuthenticationException(
                    "The authentication token is missing, it cannot be null or empty");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionToken sessionToken = (SessionToken) o;
        return value.equals(sessionToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
