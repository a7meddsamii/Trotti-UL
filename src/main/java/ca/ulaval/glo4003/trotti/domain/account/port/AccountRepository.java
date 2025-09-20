package ca.ulaval.glo4003.trotti.domain.account.port;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;

public interface AccountRepository {
  void save(Account account);
  Account findByEmail(Email email);
  Account findByIdul(String idul);
}
