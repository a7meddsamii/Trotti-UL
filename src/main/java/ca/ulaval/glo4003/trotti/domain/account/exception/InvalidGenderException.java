package ca.ulaval.glo4003.trotti.domain.account.exception;

import ca.ulaval.glo4003.trotti.domain.account.Gender;
import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class InvalidGenderException extends InvalidParameterException {

    public InvalidGenderException(String value) {
        super(ErrorType.INVALID_GENDER,
                "Invalid gender: " + value + ". Accepted values are: " + Gender.acceptedValues());
    }
}
