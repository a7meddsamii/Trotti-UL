package ca.ulaval.glo4003.trotti.application.mapper;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

class AccountMapperTest {
    private AccountFactory factory;
    private PasswordHasher hasher;
    private AccountMapper mapper;

    private CreateAccount request;

    @BeforeEach
    void setup() {
        factory = Mockito.mock(AccountFactory.class);
        hasher = Mockito.mock(PasswordHasher.class);

        mapper = new AccountMapper(factory, hasher);
    }

}
