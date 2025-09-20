package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.api.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.exception.EmailAlreadyExistsException;
import ca.ulaval.glo4003.trotti.domain.account.exception.IdulAlreadyExistsException;

public class AccountService {

  private final AccountRepository accountRepository;
  private final AccountMapper accountMapper;

  public AccountService(
    AccountRepository accountRepository,
    AccountMapper accountMapper
  ) {
    this.accountRepository = accountRepository;
    this.accountMapper = accountMapper;
  }

  public void createAccount(CreateAccount request) {
    Email email = new Email(request.email());

    validateAccountDoesNotExist(email, request.idul());

    Account account = accountMapper.create(request);
    accountRepository.save(account);
  }

  private void validateAccountDoesNotExist(Email email, String idul) {
    if (accountRepository.findByEmail(email).isPresent()) {
      throw new EmailAlreadyExistsException(email.value());
    }
    if (accountRepository.findByIdul(idul).isPresent()) {
      throw new IdulAlreadyExistsException(idul);
    }
  }
}
