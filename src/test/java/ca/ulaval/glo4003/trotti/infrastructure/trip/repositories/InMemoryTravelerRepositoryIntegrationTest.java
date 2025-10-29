package ca.ulaval.glo4003.trotti.infrastructure.trip.repositories;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.trip.domain.entities.traveler.Traveler;
import ca.ulaval.glo4003.trotti.trip.domain.repositories.TravelerRepository;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.fixtures.TravelerFixture;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.mappers.AccountPersistenceMapper;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.InMemoryAccountRepository;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import ca.ulaval.glo4003.trotti.order.infrastructure.repositories.records.BuyerRecord;
import ca.ulaval.glo4003.trotti.commons.persistence.inmemory.UserInMemoryDatabase;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.mappers.TravelerPersistenceMapper;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.InMemoryTravelerRepository;
import ca.ulaval.glo4003.trotti.trip.infrastructure.repositories.records.TravelerRecord;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTravelerRepositoryIntegrationTest {

    private static final Idul AN_IDUL = Idul.from("AN_IDUL");
    private static final Idul ANOTHER_IDUL = Idul.from("ANOTHER_IDUL");

    private TravelerRepository travelerRepository;
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        ConcurrentMap<Idul, AccountRecord> accountTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, BuyerRecord> buyerTable = new ConcurrentHashMap<>();
        ConcurrentMap<Idul, TravelerRecord> travelerTable = new ConcurrentHashMap<>();
        UserInMemoryDatabase userInMemoryDatabase =
                new UserInMemoryDatabase(accountTable, buyerTable, travelerTable);
        TravelerPersistenceMapper travelerMapper = new TravelerPersistenceMapper();
        travelerRepository = new InMemoryTravelerRepository(userInMemoryDatabase, travelerMapper);
        AccountPersistenceMapper accountMapper = new AccountPersistenceMapper();
        accountRepository = new InMemoryAccountRepository(userInMemoryDatabase, accountMapper);
    }

    @Test
    void givenATraveler_whenSaving_thenItIsSaved() {
        Traveler traveler = new TravelerFixture().withRidePermit().build();
        Account account = new AccountFixture().build();
        accountRepository.save(account);

        travelerRepository.update(traveler);
        List<Traveler> retrievedTravelers = travelerRepository.findAll();

        Assertions.assertNotNull(retrievedTravelers.get(0));
    }

    @Test
    void givenDifferentTravelers_whenSaving_thenTheyCanBeRetrieved() {
        Traveler firstTraveler = new TravelerFixture().withIdul(AN_IDUL).build();
        Traveler secondTraveler = new TravelerFixture().withIdul(ANOTHER_IDUL).build();
        Account firstAccount = new AccountFixture().withIdul(AN_IDUL).build();
        Account secondAccount = new AccountFixture().withIdul(ANOTHER_IDUL).build();
        accountRepository.save(firstAccount);
        accountRepository.save(secondAccount);

        travelerRepository.update(firstTraveler);
        travelerRepository.update(secondTraveler);
        List<Traveler> retrievedTravelers = travelerRepository.findAll();

        Assertions.assertEquals(2, retrievedTravelers.size());
        assertEquals(firstTraveler, retrievedTravelers.get(0));
        assertEquals(secondTraveler, retrievedTravelers.get(1));
    }

    @Test
    void givenATraveler_whenFindByIdul_thenReturnsTraveler() {
        Traveler traveler = new TravelerFixture().withIdul(AN_IDUL).build();
        Account account = new AccountFixture().withIdul(AN_IDUL).build();
        accountRepository.save(account);
        travelerRepository.update(traveler);

        Traveler retrievedTraveler = travelerRepository.findByIdul(AN_IDUL);

        Assertions.assertNotNull(retrievedTraveler);
        assertEquals(traveler, retrievedTraveler);
    }

    void assertEquals(Traveler savedTraveler, Traveler retrievedTraveler) {
        Assertions.assertEquals(savedTraveler.getIdul(), retrievedTraveler.getIdul());
        Assertions.assertEquals(savedTraveler.getEmail(), retrievedTraveler.getEmail());
        Assertions.assertEquals(savedTraveler.getWalletPermits().size(),
                retrievedTraveler.getWalletPermits().size());
    }
}
