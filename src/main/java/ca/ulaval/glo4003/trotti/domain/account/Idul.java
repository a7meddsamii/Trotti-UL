package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class Idul {

    private final String value;

    public static Idul from(String value) {
        return new Idul(value);
    }

    private Idul(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidParameterException("Idul is missing, it cannot be null or empty");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Idul idul = (Idul) o;
        return value.equals(idul.value);
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
