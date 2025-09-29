package ca.ulaval.glo4003.trotti.infrastructure.mappers;

import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import ca.ulaval.glo4003.trotti.infrastructure.repository.account.AccountEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersistenceAccountMapperTest {

    private PersistenceAccountMapper accountMapper;

    @BeforeEach
    void setup() {
        accountMapper = new PersistenceAccountMapper();
    }

    @Test
    void givenAccount_whenToEntity_thenReturnsPersistenceEntity() {
        Account account = new AccountFixture().build();

        AccountEntity persistenceEntity = accountMapper.toDTO(account);

        Assertions.assertEquals(account.getIdul(), persistenceEntity.idul());
        Assertions.assertEquals(account.getName(), persistenceEntity.name());
        Assertions.assertEquals(account.getBirthDate(), persistenceEntity.birthDate());
        Assertions.assertEquals(account.getGender(), persistenceEntity.gender());
        Assertions.assertEquals(account.getEmail(), persistenceEntity.email());
        Assertions.assertEquals(account.getPassword(), persistenceEntity.password());
    }

    @Test
    void givenPersistenceEntity_whenToDomain_thenReturnsAccount() {
        AccountEntity persistenceEntity = new AccountEntity(AccountFixture.AN_IDUL,
                AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE, AccountFixture.A_GENDER,
                AccountFixture.AN_EMAIL, AccountFixture.A_PASSWORD);

        Account account = accountMapper.toEntity(persistenceEntity);

        Assertions.assertEquals(persistenceEntity.idul(), account.getIdul());
        Assertions.assertEquals(persistenceEntity.name(), account.getName());
        Assertions.assertEquals(persistenceEntity.birthDate(), account.getBirthDate());
        Assertions.assertEquals(persistenceEntity.gender(), account.getGender());
        Assertions.assertEquals(persistenceEntity.email(), account.getEmail());
        Assertions.assertEquals(persistenceEntity.password(), account.getPassword());
    }
}