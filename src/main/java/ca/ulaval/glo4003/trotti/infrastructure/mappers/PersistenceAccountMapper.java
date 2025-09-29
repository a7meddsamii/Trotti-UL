package ca.ulaval.glo4003.trotti.infrastructure.mappers;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.infrastructure.repository.account.AccountEntity;

public class PersistenceAccountMapper {

    public AccountEntity toDTO(Account account) {
        return new AccountEntity(account.getIdul(), account.getName(), account.getBirthDate(),
                account.getGender(), account.getEmail(), account.getPassword());
    }

    public Account toEntity(AccountEntity accountFound) {
        return new Account(accountFound.name(), accountFound.birthDate(), accountFound.gender(),
                accountFound.idul(), accountFound.email(), accountFound.password());
    }
}