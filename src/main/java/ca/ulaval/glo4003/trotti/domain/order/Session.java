package ca.ulaval.glo4003.trotti.domain.order;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class Session {
    private final String value;

    private Session(String value) {
        validate(value);
        this.value = value;
    }

    public static Session from(String value) {
        return new Session(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Session email = (Session) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

    private void validate(String value) {

        if (StringUtils.isBlank(value)) {
            throw new InvalidParameterException("Pass session is missing, it cannot be null or empty");
        }

        if(value.matches("^[AHE]\\d{4}$")) {
            throw new InvalidParameterException("Pass session invalid");
        }
    }
}
