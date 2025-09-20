package ca.ulaval.glo4003.trotti.domain.account.auth;

import java.util.Objects;

public class AuthToken {

    private final String value;

    public static AuthToken from(String value) {
        return new AuthToken(value);
    }

    private AuthToken(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        this.value = value;
    }

    public String getValue() {
        return value.toString();
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
