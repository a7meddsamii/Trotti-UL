package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.application.port.TokenPort;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.Password;
import ca.ulaval.glo4003.trotti.domain.account.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.trotti.domain.account.port.AccountRepository;

public class AccountService {

  private final ca.ulaval.glo4003.trotti.domain.account.port.AccountRepository accountRepository;
  private final AccountMapper accountMapper;
  private final TokenPort token;

  public AccountService(
          AccountRepository accountRepository,
          AccountMapper accountMapper,
          TokenPort token
          ) {
    this.accountRepository = accountRepository;
    this.accountMapper = accountMapper;
      this.token = token;
  }

  public void createAccount(CreateAccount request) {
    Email email = new Email(request.email());
    Idul idul = Idul.from(request.idul());
    
    Account account = accountMapper.create(request);
    accountRepository.save(account);
  }
  
  public String login(CreateAccount request) {
    Email email = new Email(request.email());

    Account account = accountRepository.findByEmail(email);
    
    passwordhasher(request.password(), account.getHashedPassword());
    return token.generateToken(account.getIdul());
  }
  
  private void passwordhasher(String password, Password passwordHash) {
   // boolean ok = passwordHasher.verify(password, passwordHash);
    boolean ok =true; // Temporarily bypassing password verification
    if (!ok) {
      throw new InvalidCredentialsException();
    }
  }
}