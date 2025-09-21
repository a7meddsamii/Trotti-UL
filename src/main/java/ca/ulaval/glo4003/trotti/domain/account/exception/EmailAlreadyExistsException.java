package ca.ulaval.glo4003.trotti.domain.account.exception;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class EmailAlreadyExistsException extends InvalidParameterException {

    public EmailAlreadyExistsException(String value) {
        super(ErrorType.EMAIL_ALREADY_EXISTS, "Email already exists: " + value);
    }
}
