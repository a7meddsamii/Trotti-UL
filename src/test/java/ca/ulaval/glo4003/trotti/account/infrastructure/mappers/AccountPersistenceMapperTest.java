package ca.ulaval.glo4003.trotti.account.infrastructure.mappers;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.account.infrastructure.repositories.records.AccountRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountPersistenceMapperTest {

    private AccountPersistenceMapper accountMapper;

    @BeforeEach
    void setup() {
        accountMapper = new AccountPersistenceMapper();
    }

    @Test
    void givenAccount_whenToEntity_thenReturnPersistenceEntity() {
        Account account = new AccountFixture().build();

        AccountRecord persistenceEntity = accountMapper.toDTO(account);

        Assertions.assertEquals(account.getIdul(), persistenceEntity.idul());
        Assertions.assertEquals(account.getName(), persistenceEntity.name());
        Assertions.assertEquals(account.getBirthDate(), persistenceEntity.birthDate());
        Assertions.assertEquals(account.getGender(), persistenceEntity.gender());
        Assertions.assertEquals(account.getEmail(), persistenceEntity.email());
        Assertions.assertEquals(account.getRole(), persistenceEntity.role());
        Assertions.assertEquals(account.getPermissions(), persistenceEntity.permissions());
    }

    @Test
    void givenPersistenceEntity_whenToDomain_thenReturnAccount() {
        AccountRecord persistenceEntity = new AccountRecord(AccountFixture.IDUL,
                AccountFixture.NAME, AccountFixture.BIRTHDATE, AccountFixture.GENDER,
                AccountFixture.EMAIL, AccountFixture.ROLE, AccountFixture.SET_OF_PERMISSION,
                AccountFixture.SET_OF_ADVANTAGES);

        Account account = accountMapper.toEntity(persistenceEntity);

        Assertions.assertEquals(persistenceEntity.idul(), account.getIdul());
        Assertions.assertEquals(persistenceEntity.name(), account.getName());
        Assertions.assertEquals(persistenceEntity.birthDate(), account.getBirthDate());
        Assertions.assertEquals(persistenceEntity.gender(), account.getGender());
        Assertions.assertEquals(persistenceEntity.email(), account.getEmail());
        Assertions.assertEquals(persistenceEntity.role(), account.getRole());
        Assertions.assertEquals(persistenceEntity.permissions(), account.getPermissions());
    }
}
