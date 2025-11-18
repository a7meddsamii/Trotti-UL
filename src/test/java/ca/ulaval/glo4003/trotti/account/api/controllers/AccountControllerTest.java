package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.application.AccountService;
import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountControllerTest {
    private static final String ACCOUNTS_ENDPOINT = "/api/accounts";
    private static final String PATH_SEPARATOR = "/";

    private AccountService accountApplicationService;
    private AccountApiMapper accountApiMapper;
    private AccountDto mappedDto;
    private CreateAccountRequest request;

    private AccountResource accountController;

    @BeforeEach
    void setUp() {
        accountApplicationService = Mockito.mock(AccountService.class);
        accountApiMapper = Mockito.mock(AccountApiMapper.class);
        mappedDto = Mockito.mock(AccountDto.class);
        request = buildValidRequest();
        Mockito.when(accountApiMapper.toAccountDto(request)).thenReturn(mappedDto);

        accountController = new AccountController(accountApplicationService, accountApiMapper);
    }

    @Test
    void givenValidCreateAccountRequest_whenCreateAccount_thenReturns201CreatedWithLocationHeaderWithRequestIdul() {
        Mockito.when(accountApiMapper.toAccountDto(request)).thenReturn(mappedDto);

        Response response = accountController.createAccount(request);

        Mockito.verify(accountApplicationService).createAccount(mappedDto);
        Assertions.assertEquals(URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + request.idul()),
                response.getLocation());
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

    }

    private CreateAccountRequest buildValidRequest() {
        return new CreateAccountRequest(AccountFixture.A_NAME, AccountFixture.A_STRING_BIRTHDATE,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD,
                AccountFixture.A_ROLE_STRING);
    }
}
