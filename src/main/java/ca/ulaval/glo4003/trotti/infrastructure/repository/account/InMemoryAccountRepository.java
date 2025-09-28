package ca.ulaval.glo4003.trotti.infrastructure.repository.account;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.mappers.PersistenceAccountMapper;
import ca.ulaval.glo4003.trotti.infrastructure.repository.UserInMemoryDatabase;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
    private final UserInMemoryDatabase database;
    private final PersistenceAccountMapper accountMapper;

    public InMemoryAccountRepository(
            UserInMemoryDatabase database,
            PersistenceAccountMapper accountMapper) {
        this.database = database;
        this.accountMapper = accountMapper;
    }

    @Override
    public void save(Account account) {
        AccountEntity accountEntity = accountMapper.toEntity(account);
        this.database.insertIntoAccountTable(accountEntity);
    }

    @Override
    public Optional<Account> findByEmail(Email email) {
        Optional<AccountEntity> accountQuery = database.selectFromAccountTable(email);
        return accountQuery.map(this.accountMapper::toDomain);
    }

    @Override
    public Optional<Account> findByIdul(Idul idul) {
        Optional<AccountEntity> accountQuery = database.selectFromAccountTable(idul);
        return accountQuery.map(this.accountMapper::toDomain);
    }
}
