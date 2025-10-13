package ca.ulaval.glo4003.trotti.infrastructure.account.mappers;

import ca.ulaval.glo4003.trotti.domain.account.entities.Account;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.records.AccountRecord;

public class AccountPersistenceMapper {

    public AccountRecord toDTO(Account account) {
        return new AccountRecord(account.getIdul(), account.getName(), account.getBirthDate(),
                account.getGender(), account.getEmail(), account.getPassword());
    }

    public Account toEntity(AccountRecord accountFound) {
        return new Account(accountFound.name(), accountFound.birthDate(), accountFound.gender(),
                accountFound.idul(), accountFound.email(), accountFound.password());
    }
}
