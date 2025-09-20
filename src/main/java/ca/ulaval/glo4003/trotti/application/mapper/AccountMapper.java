package ca.ulaval.glo4003.trotti.application.mapper;

import ca.ulaval.glo4003.trotti.api.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.domain.account.*;

public class AccountMapper {

  private final AccountFactory accountFactory;

  private AccountMapper(AccountFactory accountFactory) {
    this.accountFactory = accountFactory;
  }

  public Account create(CreateAccount request) {
    String passwordHash = hashValidatedPassword(request);
    Email email = new Email(request.email());
    Gender gender = Gender.fromString(request.gender());

    return accountFactory.create(
      request.name(),
      request.birthDate(),
      gender,
      request.idul(),
      email,
      passwordHash
    );
  }

  private String hashValidatedPassword(CreateAccount request) {
    Password password = new Password(request.password());
    String hashedPassword = "passwordHasher.hashPassword(rawPassword.value())"; // TODO

    return hashedPassword;
  }
}
