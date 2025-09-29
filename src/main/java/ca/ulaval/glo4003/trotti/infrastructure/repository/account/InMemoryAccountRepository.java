package ca.ulaval.glo4003.trotti.infrastructure.repository.account;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.commons.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.repository.UserInMemoryDatabase;
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