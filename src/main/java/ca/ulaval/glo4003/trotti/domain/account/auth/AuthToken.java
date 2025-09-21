package ca.ulaval.glo4003.trotti.domain.account.auth;

import ca.ulaval.glo4003.trotti.domain.account.exception.MalformedTokenException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class AuthToken {

    private final String value;

    public static AuthToken from(String value) {
        return new AuthToken(value);
    }

    private AuthToken(String value) {
        if (StringUtils.isBlank(value)) {
            throw new MalformedTokenException(
                    "The authentication token is missing, it cannot be null or empty");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthToken authToken = (AuthToken) o;
        return value.equals(authToken.value);
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
