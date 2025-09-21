package ca.ulaval.glo4003.trotti.domain.account.exception;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class InvalidPasswordException extends InvalidParameterException {

    public InvalidPasswordException() {
        super(ErrorType.INVALID_PASSWORD,
                "Invalid password: it must contain at least 10 characters, one uppercase letter, one digit, and one special character.");
    }
}
