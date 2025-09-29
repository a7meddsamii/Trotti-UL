package ca.ulaval.glo4003.trotti.domain.authentication;

import ca.ulaval.glo4003.trotti.domain.account.exceptions.MalformedTokenException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class AuthenticationToken {

    private final String value;

    public static AuthenticationToken from(String value) {
        return new AuthenticationToken(value);
    }

    private AuthenticationToken(String value) {
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

        AuthenticationToken authenticationToken = (AuthenticationToken) o;
        return value.equals(authenticationToken.value);
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
