package ca.ulaval.glo4003.trotti.domain.account.exception;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class IdulAlreadyExistsException extends InvalidParameterException {

  public IdulAlreadyExistsException(String value) {
    super(ErrorType.IDUL_ALREADY_EXISTS, "IDUL already exists: " + value);
  }
}
