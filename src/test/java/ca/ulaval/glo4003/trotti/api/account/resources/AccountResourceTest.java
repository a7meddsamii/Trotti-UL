package ca.ulaval.glo4003.trotti.api.account.resources;

import ca.ulaval.glo4003.trotti.api.account.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.api.account.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.account.dto.AccountDto;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountResourceTest {
    private static final String ACCOUNTS_ENDPOINT = "/api/accounts";
    private static final String PATH_SEPARATOR = "/";

    private AccountApplicationService accountApplicationService;
    private AccountApiMapper accountApiMapper;
    private AccountDto mappedDto;
    private CreateAccountRequest request;

    private AccountResource accountResource;

    @BeforeEach
    void setUp() {
        accountApplicationService = Mockito.mock(AccountApplicationService.class);
        accountApiMapper = Mockito.mock(AccountApiMapper.class);
        mappedDto = Mockito.mock(AccountDto.class);
        request = buildValidRequest();
        Mockito.when(accountApiMapper.toAccountDto(request)).thenReturn(mappedDto);

        accountResource = new AccountResource(accountApplicationService, accountApiMapper);
    }

    @Test
    void givenValidCreateAccountRequest_whenCreateAccount_thenReturns201Created() {
        AccountDto dto = Mockito.mock(AccountDto.class);
        Mockito.when(accountApiMapper.toAccountDto(request)).thenReturn(dto);

        Response response = accountResource.createAccount(request);

        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

    }

    @Test
    void givenValidCreateAccountRequest_whenCreateAccount_thenSetsLocationHeaderWithRequestIdul() {
        Response response = accountResource.createAccount(request);

        Assertions.assertEquals(URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + request.idul()),
                response.getLocation());
    }

    @Test
    void givenValidRequest_whenCreateAccount_thenServiceIsCalled() {

        accountResource.createAccount(request);

        Mockito.verify(accountApplicationService).createAccount(mappedDto);
    }

    @Test
    void givenValidCreateAccountRequest_whenCreateAccount_thenMapperIsCalledWithRequest() {

        accountResource.createAccount(request);

        Mockito.verify(accountApiMapper).toAccountDto(request);
    }

    private CreateAccountRequest buildValidRequest() {
        return new CreateAccountRequest(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
    }
}
