package ca.ulaval.glo4003.trotti.domain.account.exception;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class InvalidEmailException extends InvalidParameterException {

  public InvalidEmailException(String value) {
    super(
      ErrorType.INVALID_EMAIL,
      "Invalid email or incorrect domain: " + value
    );
  }
}
