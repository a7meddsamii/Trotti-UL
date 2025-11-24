package ca.ulaval.glo4003.trotti.account.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.values.Advantage;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.infrastructure.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<Idul, AccountRecord> accountTable;
    private final AccountPersistenceMapper accountMapper;

    public InMemoryAccountRepository(
            Map<Idul, AccountRecord> accountTable,
            AccountPersistenceMapper accountMapper) {
        this.accountTable = accountTable;
        this.accountMapper = accountMapper;
    }

    @Override
    public void save(Account account) {
        AccountRecord accountRecord = accountMapper.toDTO(account);
        accountTable.put(accountRecord.idul(), accountRecord);
    }

    @Override
    public Optional<Account> findByEmail(Email email) {
        return accountTable.values().stream()
                .filter(accountRecord -> accountRecord.email().equals(email)).findFirst()
                .map(this.accountMapper::toEntity);
    }

    @Override
    public Optional<Account> findByIdul(Idul idul) {
        return Optional.ofNullable(accountTable.get(idul)).map(this.accountMapper::toEntity);
    }

    @Override
    public List<Account> findAllByAdvantage(Advantage advantage) {
        return accountTable.values().stream()
                .filter(accountRecord -> accountRecord.advantages().contains(advantage))
                .map(this.accountMapper::toEntity).toList();
    }
}
