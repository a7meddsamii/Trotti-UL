package ca.ulaval.glo4003.trotti.domain.account.repository;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.util.Optional;

public interface AccountRepository {
    void save(Account account);

    Optional<Account> findByEmail(Email email);

    Optional<Account> findByIdul(Idul idul);
}
