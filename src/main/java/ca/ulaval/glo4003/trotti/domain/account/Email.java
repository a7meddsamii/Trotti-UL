package ca.ulaval.glo4003.trotti.domain.account;


import ca.ulaval.glo4003.trotti.domain.account.exception.InvalidEmailException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public record Email(String value) {
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
    "^[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@ulaval\\.ca$"
  );

  public Email {
    if (StringUtils.isBlank(value) || !EMAIL_PATTERN.matcher(value).matches()) {
      throw new InvalidEmailException(value);
    }
  }
}
