package ca.ulaval.glo4003.trotti.domain.account;

import ca.ulaval.glo4003.trotti.domain.account.exception.InvalidPasswordException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public record Password(String value) {
  private static final Pattern PASSWORD_PATTERN = Pattern.compile(
    "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-]).{10,}$"
  );

  public Password {
    if (StringUtils.isBlank(value) || !PASSWORD_PATTERN.matcher(value).matches()) {
      throw new InvalidPasswordException();
    }
  }
}
