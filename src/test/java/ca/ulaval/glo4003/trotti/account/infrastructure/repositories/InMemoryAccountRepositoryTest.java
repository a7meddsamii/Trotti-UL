package ca.ulaval.glo4003.trotti.account.infrastructure.repositories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.Advantage;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.account.infrastructure.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InMemoryAccountRepositoryTest {

    private static final Idul ACCOUNT_IDUL = Idul.from("jdoe123");
    private static final Email ACCOUNT_EMAIL = Email.from("john.doe@ulaval.ca");
    private static final Advantage FREE_RIDE_PERMIT = Advantage.FREE_RIDE_PERMIT;

    private Map<Idul, AccountRecord> accountTable;
    private AccountPersistenceMapper accountMapper;
    private InMemoryAccountRepository repository;
    private Account account;
    private AccountRecord accountRecord;

    @BeforeEach
    void setup() {
        accountTable = new HashMap<>();
        accountMapper = Mockito.mock(AccountPersistenceMapper.class);
        repository = new InMemoryAccountRepository(accountTable, accountMapper);

        account = new AccountFixture().withIdul(ACCOUNT_IDUL).build();
        accountRecord = new AccountRecord(account.getIdul(), account.getName(),
                account.getBirthDate(), account.getGender(), account.getEmail(), account.getRole(),
                account.getPermissions(), Set.of(FREE_RIDE_PERMIT));
    }

    @Test
    void givenAccount_whenSave_thenAccountIsStoredInTable() {
        Mockito.when(accountMapper.toDTO(account)).thenReturn(accountRecord);

        repository.save(account);

        Assertions.assertEquals(accountRecord, accountTable.get(ACCOUNT_IDUL));
    }

    @Test
    void givenExistingEmail_whenFindByEmail_thenReturnsAccount() {
        accountTable.put(ACCOUNT_IDUL, accountRecord);
        Mockito.when(accountMapper.toEntity(accountRecord)).thenReturn(account);

        Optional<Account> result = repository.findByEmail(ACCOUNT_EMAIL);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(account, result.get());
    }

    @Test
    void givenNonExistingEmail_whenFindByEmail_thenReturnsEmpty() {
        Email nonExistingEmail = Email.from("nonexisting@ulaval.ca");

        Optional<Account> result = repository.findByEmail(nonExistingEmail);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenExistingIdul_whenFindByIdul_thenReturnsAccount() {
        accountTable.put(ACCOUNT_IDUL, accountRecord);
        Mockito.when(accountMapper.toEntity(accountRecord)).thenReturn(account);

        Optional<Account> result = repository.findByIdul(ACCOUNT_IDUL);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(account, result.get());
    }

    @Test
    void givenNonExistingIdul_whenFindByIdul_thenReturnsEmpty() {
        Idul nonExistingIdul = Idul.from("nonexisting");

        Optional<Account> result = repository.findByIdul(nonExistingIdul);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenAccountsWithAdvantage_whenFindAllByAdvantage_thenReturnsMatchingAccounts() {
        accountTable.put(ACCOUNT_IDUL, accountRecord);
        Mockito.when(accountMapper.toEntity(accountRecord)).thenReturn(account);

        List<Account> result = repository.findAllByAdvantage(FREE_RIDE_PERMIT);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(account, result.get(0));
    }
}
