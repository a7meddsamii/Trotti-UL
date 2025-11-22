package ca.ulaval.glo4003.trotti.account.domain.repositories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Role;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    void save(Account account);

    Optional<Account> findByEmail(Email email);

    Optional<Account> findByIdul(Idul idul);

    List<Account> findByRole(Role role);
}
