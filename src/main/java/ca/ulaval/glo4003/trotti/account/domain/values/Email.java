package ca.ulaval.glo4003.trotti.account.domain.values;

import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * @deprecated Use the version in {@link ca.ulaval.glo4003.trotti.commons.domain.Email} instead.
 *             This one will be delete in a subsequent Pull Request
 * 
 */
public class Email {
    private static final String ULAVAL_DOMAIN = "@ulaval.ca";
    private final String value;

    private Email(String value) {
        validate(value);
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

    private void validate(String value) {
        if (StringUtils.isBlank(value)) {
            throw new InvalidParameterException("Email cannot be null or empty" + value);
        }

        boolean isValidEmail = EmailValidator.getInstance().isValid(value)
                && value.toLowerCase().endsWith(ULAVAL_DOMAIN);

        if (!isValidEmail) {
            throw new InvalidParameterException("Invalid email or incorrect domain: ");
        }
    }
}
