package ca.ulaval.glo4003.trotti.infrastructure.repository.account;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import java.util.Optional;

public class PersistenceAccountMapper {

    public AccountEntity toEntity(Account account) {
        return new AccountEntity(account.getIdul(), account.getName(), account.getBirthDate(),
                account.getGender(), account.getEmail(), account.getPassword());
    }

    public Optional<Account> toDomain(AccountEntity accountFound) {
        Account account =
                new Account(accountFound.name(), accountFound.birthDate(), accountFound.gender(),
                        accountFound.idul(), accountFound.email(), accountFound.password());

        return Optional.of(account);
    }
}
