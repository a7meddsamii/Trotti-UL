package ca.ulaval.glo4003.trotti.infrastructure.order.repositories;

import ca.ulaval.glo4003.trotti.domain.account.entities.Account;
import ca.ulaval.glo4003.trotti.domain.account.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.order.entities.buyer.Buyer;
import ca.ulaval.glo4003.trotti.domain.order.repositories.BuyerRepository;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.fixtures.BuyerFixture;
import ca.ulaval.glo4003.trotti.infrastructure.account.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.infrastructure.account.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.order.mappers.BuyerPersistenceMapper;
import ca.ulaval.glo4003.trotti.infrastructure.order.repositories.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.infrastructure.persistence.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.infrastructure.trip.repositories.records.TravelerRecord;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryBuyerRepositoryIntegrationTest {

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

    private void assertEquals(Buyer expected, Buyer actual) {
        Assertions.assertEquals(expected.getIdul(), actual.getIdul());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getPaymentMethod().get().getSecuredString(),
                actual.getPaymentMethod().get().getSecuredString());
        Assertions.assertEquals(expected.getCart().getPasses(), actual.getCart().getPasses());
    }
}
