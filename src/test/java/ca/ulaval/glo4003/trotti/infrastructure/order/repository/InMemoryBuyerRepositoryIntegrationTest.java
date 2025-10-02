package ca.ulaval.glo4003.trotti.infrastructure.order.repository;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.fixtures.BuyerFixture;
import ca.ulaval.glo4003.trotti.infrastructure.account.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.account.repository.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.account.repository.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.repository.record.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.trip.records.TravelerRecord;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class InMemoryBuyerRepositoryIntegrationTest {
    private static final Idul IDUL_OF_NON_EXISTING_BUYER = Idul.from("NONEXIST");
    private static final Email EMAIL_OF_NON_EXISTING_BUYER = Email.from("nonexisting@ulaval.ca");
    private BuyerRepository buyerRepository;
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        ConcurrentMap<Idul, AccountRecord> accountTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, BuyerRecord> buyerTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, TravelerRecord> travelerTable = new ConcurrentHashMap<>();
        UserInMemoryDatabase userInMemoryDatabase =
                new UserInMemoryDatabase(accountTable, buyerTable, travelerTable);
        BuyerPersistenceMapper buyerMapper = new BuyerPersistenceMapper();
        buyerRepository = new InMemoryBuyerRepository(userInMemoryDatabase, buyerMapper);
        AccountPersistenceMapper accountMapper = new AccountPersistenceMapper();
        accountRepository = new InMemoryAccountRepository(userInMemoryDatabase, accountMapper);
    }

    @Test
    void givenABuyer_whenSaving_thenItIsSaved() {
        Buyer buyer = new BuyerFixture().buildWithPaymentMethod();
        Account account = new AccountFixture().build();
        accountRepository.save(account);

        buyerRepository.update(buyer);
        Buyer retrievedBuyer = buyerRepository.findByIdul(buyer.getIdul());

        Assertions.assertNotNull(retrievedBuyer);
    }

    @Test
    void givenABuyer_whenSaving_thenItCanBeRetrievedByIdul() {
        Buyer buyer = new BuyerFixture().buildWithPaymentMethod();
        Account account = new AccountFixture().build();
        accountRepository.save(account);

        buyerRepository.update(buyer);
        Buyer retrievedBuyer = buyerRepository.findByIdul(buyer.getIdul());

        Assertions.assertNotNull(retrievedBuyer);
        assertEquals(buyer, retrievedBuyer);
    }

    @Test
    void givenABuyer_whenSaving_thenItCanBeRetrievedByEmail() {
        Buyer buyer = new BuyerFixture().buildWithPaymentMethod();
        Account account = new AccountFixture().build();
        accountRepository.save(account);

        buyerRepository.update(buyer);
        Buyer retrievedBuyer = buyerRepository.findByEmail(buyer.getEmail());

        Assertions.assertNotNull(retrievedBuyer);
        assertEquals(buyer, retrievedBuyer);
    }

    @Test
    void givenIdulOfNonExistingBuyer_whenFindingByIdul_thenExceptionIsThrown() {
        Executable executable = () -> buyerRepository.findByIdul(IDUL_OF_NON_EXISTING_BUYER);

        Assertions.assertThrows(NullPointerException.class, executable);
    }

    @Test
    void givenEmailOfNonExistingBuyer_whenFindingByEmail_thenExceptionIsThrown() {
        Executable executable = () -> buyerRepository.findByEmail(EMAIL_OF_NON_EXISTING_BUYER);

        Assertions.assertThrows(NullPointerException.class, executable);
    }

    private void assertEquals(Buyer expected, Buyer actual) {
        Assertions.assertEquals(expected.getIdul(), actual.getIdul());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getPaymentMethod().get().getSecuredString(), actual.getPaymentMethod().get().getSecuredString());
        Assertions.assertEquals(expected.getCart().getPasses(), actual.getCart().getPasses());
    }
}
