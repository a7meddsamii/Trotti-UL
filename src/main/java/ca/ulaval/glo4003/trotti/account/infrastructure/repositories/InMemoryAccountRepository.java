package ca.ulaval.glo4003.trotti.account.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.account.infrastructure.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.commons.infrastructure.database.UserInMemoryDatabase;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
    private final UserInMemoryDatabase databaseDriver;
    private final AccountPersistenceMapper accountMapper;

    public InMemoryAccountRepository(
            UserInMemoryDatabase database,
            AccountPersistenceMapper accountMapper) {
        this.databaseDriver = database;
        this.accountMapper = accountMapper;
    }

    @Override
    public void save(Account account) {
        AccountRecord accountRecord = accountMapper.toDTO(account);
        this.databaseDriver.insertIntoAccountTable(accountRecord);
    }

    @Override
    public Optional<Account> findByEmail(Email email) {
        Optional<AccountRecord> accountQuery = databaseDriver.selectFromAccountTable(email);
        return accountQuery.map(this.accountMapper::toEntity);
    }

    @Override
    public Optional<Account> findByIdul(Idul idul) {
        Optional<AccountRecord> accountQuery = databaseDriver.selectFromAccountTable(idul);
        return accountQuery.map(this.accountMapper::toEntity);
    }
}
