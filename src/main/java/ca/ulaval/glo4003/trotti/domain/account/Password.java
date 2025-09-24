package ca.ulaval.glo4003.trotti.domain.account;

import java.util.Objects;

public class Password {

    private final PasswordHasher hasher;
    private final String hasedvalue;

    public Password(String hasedvalue, PasswordHasher hasher) {
        this.hasher = hasher;
        this.hasedvalue = hasedvalue;
    }

    public boolean matches(String rawPassword) {
        return hasher.verify(rawPassword, this.hasedvalue);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return hasedvalue.equals(password.hasedvalue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasedvalue);
    }

    @Override
    public String toString() {
        return hasedvalue;
    }
}
