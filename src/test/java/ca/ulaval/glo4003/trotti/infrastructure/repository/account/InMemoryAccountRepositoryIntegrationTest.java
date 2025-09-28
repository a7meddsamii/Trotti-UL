package ca.ulaval.glo4003.trotti.infrastructure.repository.account;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.mappers.PersistenceAccountMapper;
import ca.ulaval.glo4003.trotti.infrastructure.repository.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.repository.order.BuyerEntity;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryAccountRepositoryIntegrationTest {

    public static final Idul IDUL_OF_NON_EXISTING_ACCOUNT = Idul.from("NONEXIST");
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        ConcurrentMap<Idul, AccountEntity> accountTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, BuyerEntity> buyerTable = new ConcurrentHashMap<>();
        UserInMemoryDatabase userInMemoryDatabase =
                new UserInMemoryDatabase(accountTable, buyerTable);
        PersistenceAccountMapper accountMapper = new PersistenceAccountMapper();
        accountRepository = new InMemoryAccountRepository(userInMemoryDatabase, accountMapper);
    }

    @Test
    void givenAnAccount_whenSaving_thenItIsSaved() {
        Account account = new AccountFixture().build();

        accountRepository.save(account);
        Optional<Account> retrievedAccount = accountRepository.findByIdul(account.getIdul());

        Assertions.assertTrue(retrievedAccount.isPresent());
    }

    @Test
    void givenAnAccount_whenSaving_thenItCanBeRetrievedByIdul() {
        Account account = new AccountFixture().build();

        accountRepository.save(account);
        Optional<Account> retrievedAccount = accountRepository.findByIdul(account.getIdul());

        Assertions.assertTrue(retrievedAccount.isPresent());
        assertEquals(account, retrievedAccount.get());
    }

    @Test
    void givenAnAccount_whenSaving_thenItCanBeRetrievedByEmail() {
        Account account = new AccountFixture().build();

        accountRepository.save(account);
        Optional<Account> retrievedAccount = accountRepository.findByEmail(account.getEmail());

        Assertions.assertTrue(retrievedAccount.isPresent());
        assertEquals(account, retrievedAccount.get());
    }

    @Test
    void givenIdulOfNonExistingAccount_whenFindingByIdul_thenReturnsEmpty() {
        Optional<Account> retrievedAccount =
                accountRepository.findByIdul(IDUL_OF_NON_EXISTING_ACCOUNT);

        Assertions.assertTrue(retrievedAccount.isEmpty());
    }

    @Test
    void givenIdulOfNonExistingAccount_whenFindingByEmail_thenReturnsEmpty() {
        Optional<Account> retrievedAccount = accountRepository.findByEmail(AccountFixture.AN_EMAIL);

        Assertions.assertTrue(retrievedAccount.isEmpty());
    }

    private void assertEquals(Account account, Account retrievedAccount) {
        Assertions.assertEquals(account.getIdul(), retrievedAccount.getIdul());
        Assertions.assertEquals(account.getEmail(), retrievedAccount.getEmail());
        Assertions.assertEquals(account.getBirthDate(), retrievedAccount.getBirthDate());
        Assertions.assertEquals(account.getGender(), retrievedAccount.getGender());
        Assertions.assertEquals(account.getName(), retrievedAccount.getName());
        Assertions.assertEquals(account.getPassword(), retrievedAccount.getPassword());
    }
}
