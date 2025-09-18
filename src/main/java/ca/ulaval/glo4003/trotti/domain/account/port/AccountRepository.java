package ca.ulaval.glo4003.trotti.domain.account.port;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Email;

public interface AccountRepository {
  void save(Account account);
  Account findByEmail(Email email);
  Account findByIdul(String idul);
}
