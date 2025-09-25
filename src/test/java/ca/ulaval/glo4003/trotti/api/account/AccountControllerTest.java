package ca.ulaval.glo4003.trotti.api.account;

import ca.ulaval.glo4003.trotti.api.account.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.application.account.AccountService;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    private static final String ACCOUNTS_ENDPOINT = "/api/accounts";
    private static final String PATH_SEPARATOR = "/";

    private AccountService service;
    private AccountController controller;

    @BeforeEach
    void setup() {
        service = Mockito.mock(AccountService.class);
        controller = new AccountController(service);
    }

    @Test
    void givenValidRequest_whenCreateAccount_thenReturnCreatedResponseWithLocation() {
        CreateAccountRequest request = buildValidRequest();

        Response response = controller.createAccount(request);

        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Assertions.assertEquals(URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + request.idul()),
                response.getLocation());
    }

    @Test
    void givenValidRequest_whenCreateAccount_thenServiceIsCalled() {
        CreateAccountRequest request = buildValidRequest();

        controller.createAccount(request);
        Mockito.verify(service).createAccount(Mockito.any());
    }

    private CreateAccountRequest buildValidRequest() {
        return new CreateAccountRequest(AccountFixture.A_NAME, AccountFixture.A_BIRTHDATE,
                AccountFixture.A_GENDER_STRING, AccountFixture.AN_IDUL_STRING,
                AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
    }
}
