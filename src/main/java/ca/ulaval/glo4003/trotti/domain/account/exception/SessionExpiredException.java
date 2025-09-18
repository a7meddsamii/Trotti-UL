package ca.ulaval.glo4003.trotti.domain.account.exception;

import ca.ulaval.glo4003.trotti.domain.shared.exception.ErrorType;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class SessionExpiredException extends InvalidParameterException {

  public SessionExpiredException() {
    super(
      ErrorType.SESSION_EXPIRED,
      "Session has expired. Please log in again."
    );
  }
}
