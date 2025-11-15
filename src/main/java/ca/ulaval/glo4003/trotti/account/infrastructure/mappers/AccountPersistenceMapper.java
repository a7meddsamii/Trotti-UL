package ca.ulaval.glo4003.trotti.account.infrastructure.mappers;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;

public class AccountPersistenceMapper {

    public AccountRecord toDTO(Account account) {
        return new AccountRecord(account.getIdul(), account.getName(), account.getBirthDate(),
                account.getGender(), account.getEmail(), account.getPassword(), account.getRole(), account.getPermissions());
    }

    public Account toEntity(AccountRecord accountFound) {
        return new Account(accountFound.name(), accountFound.birthDate(), accountFound.gender(),
                accountFound.idul(), accountFound.email(), accountFound.password(), accountFound.role(), accountFound.permissions() );
    }
}
