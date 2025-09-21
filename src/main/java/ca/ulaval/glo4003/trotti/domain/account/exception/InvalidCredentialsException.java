package ca.ulaval.glo4003.trotti.domain.account.exception;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class InvalidCredentialsException extends InvalidParameterException {

    public InvalidCredentialsException() {
        super(ErrorType.INVALID_CREDENTIALS, "Invalid email or password.");
    }
}
