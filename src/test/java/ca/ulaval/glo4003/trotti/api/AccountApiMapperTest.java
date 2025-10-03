package ca.ulaval.glo4003.trotti.api;

import ca.ulaval.glo4003.trotti.api.dto.requests.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.application.account.dto.AccountDto;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountApiMapperTest {
    public static final String HASHED_PASSWORD = "hashed-password";

    private PasswordHasher passwordHasher;
    private AccountApiMapper accountApiMapper;

    @BeforeEach
    void setup() {
        passwordHasher = Mockito.mock(PasswordHasher.class);
        accountApiMapper = new AccountApiMapper(passwordHasher);
    }

    @Test
    void givenValidRequest_whenToAccountDto_thenReturnFullyMappedAccountDto() {
        CreateAccountRequest request = buildValidRequest();
        Mockito.when(passwordHasher.hash(AccountFixture.A_RAW_PASSWORD))
                .thenReturn(HASHED_PASSWORD);

        AccountDto dto = accountApiMapper.toAccountDto(request);

        Assertions.assertEquals(request.name(), dto.name());
        Assertions.assertEquals(request.birthDate(), dto.birthDate());
        Assertions.assertEquals(request.gender(), dto.gender().name());
        Assertions.assertEquals(request.idul(), dto.idul().toString());
        Assertions.assertEquals(request.email(), dto.email().toString());
        Assertions.assertEquals(HASHED_PASSWORD, dto.password().toString());

    }

    private CreateAccountRequest buildValidRequest() {
        return new CreateAccountRequest(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
    }
}
