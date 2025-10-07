package ca.ulaval.glo4003.trotti.infrastructure.account.repositories;

import ca.ulaval.glo4003.trotti.domain.account.entities.Account;
import ca.ulaval.glo4003.trotti.domain.account.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.infrastructure.account.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.UserInMemoryDatabase;
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
