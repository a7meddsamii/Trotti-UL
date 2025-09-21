package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class Email {
    private static final String ULAVAL_DOMAIN = "@ulaval.ca";
    private final String value;

    private Email(String value) {
        boolean isEmpty = StringUtils.isBlank(value);
        boolean goodFormat = EmailValidator.getInstance().isValid(value)
                && value.toLowerCase().endsWith(ULAVAL_DOMAIN);

        if (isEmpty || !goodFormat) {
            throw new InvalidParameterException("Invalid email or incorrect domain: ");
        }

        this.value = value;
    }

    public static Email from(String value) {
        return new Email(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Email email = (Email) o;
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
}
