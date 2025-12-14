package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountControllerTest {
    private static final String ACCOUNTS_ENDPOINT = "/api/accounts";
    private static final String PATH_SEPARATOR = "/";
    private static final Idul CREATED_ACCOUNT_IDUL = Idul.from("created-account-idul");
    private static final Idul ADMIN_MANAGED_ACCOUNT_IDUL = Idul.from("admin-managed-account-idul");

    private AccountApplicationService accountApplicationService;
    private AccountApiMapper accountApiMapper;
    private RegistrationDto mappedDto;
    private CreateAccountRequest request;

    private AccountResource accountController;

    @BeforeEach
    void setUp() {
        accountApplicationService = Mockito.mock(AccountApplicationService.class);
        accountApiMapper = Mockito.mock(AccountApiMapper.class);
        mappedDto = Mockito.mock(RegistrationDto.class);
        request = buildValidRequest();
        Mockito.when(accountApiMapper.toPasswordRegistrationDto(request)).thenReturn(mappedDto);

        accountController = new AccountController(accountApplicationService, accountApiMapper);
        Mockito.when(accountApplicationService.createAccount(mappedDto))
                .thenReturn(CREATED_ACCOUNT_IDUL);
        Mockito.when(accountApplicationService.createAdminManagedAccount(mappedDto,
                ADMIN_MANAGED_ACCOUNT_IDUL)).thenReturn(CREATED_ACCOUNT_IDUL);
    }

    @Test
    void givenValidCreateAccountRequest_whenCreateAccount_thenReturns201CreatedWithLocationHeaderWithRequestIdul() {
        Mockito.when(accountApiMapper.toPasswordRegistrationDto(request)).thenReturn(mappedDto);

        Response response = accountController.createAccount(request);

        Mockito.verify(accountApplicationService).createAccount(mappedDto);
        Assertions.assertEquals(
                URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + CREATED_ACCOUNT_IDUL),
                response.getLocation());
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    void givenValidCreateAdminManagedRequest_whenCreateAdminManagedAccount_thenReturns201CreatedWithLocationHeaderWithRequestIdul() {
        Response response =
                accountController.createAdminManagedAccount(ADMIN_MANAGED_ACCOUNT_IDUL, request);

        Mockito.verify(accountApiMapper).toPasswordRegistrationDto(request);
        Assertions.assertEquals(
                URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + CREATED_ACCOUNT_IDUL),
                response.getLocation());
        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    private CreateAccountRequest buildValidRequest() {
        return new CreateAccountRequest(AccountFixture.NAME, AccountFixture.STRING_BIRTHDATE,
                AccountFixture.GENDER_STRING, AccountFixture.IDUL_STRING,
                AccountFixture.EMAIL_STRING, AccountFixture.RAW_PASSWORD,
                AccountFixture.ROLE_STRING);
    }
}
