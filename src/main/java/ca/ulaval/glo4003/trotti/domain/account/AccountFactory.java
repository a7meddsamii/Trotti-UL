package ca.ulaval.glo4003.trotti.domain.account;

import java.time.LocalDate;

public class AccountFactory {

  public Account create(
    String name,
    LocalDate birthDate,
    Gender gender,
    String idul,
    Email email,
    String hashedPassword
  ) {
    return new Account(name, birthDate, gender, idul, email, hashedPassword);
  }
}
