package ca.ulaval.glo4003.trotti.domain.account.exception;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class InvalidIdulException extends InvalidParameterException {

  public InvalidIdulException(String value) {
    super(ErrorType.INVALID_IDUL, "Invalid idul: " + value);
  }
}
