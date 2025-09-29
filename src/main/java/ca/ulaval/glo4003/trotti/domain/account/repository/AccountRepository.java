package ca.ulaval.glo4003.trotti.domain.account.repository;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.Email;

public interface AccountRepository {
    void save(Account account);

    Account findByEmail(Email email);

    Account findByIdul(Idul idul);
}
