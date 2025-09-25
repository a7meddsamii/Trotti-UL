package ca.ulaval.glo4003.trotti.api.account;

import ca.ulaval.glo4003.trotti.api.account.dto.request.LoginRequest;
import ca.ulaval.glo4003.trotti.api.account.dto.response.LoginResponse;
import ca.ulaval.glo4003.trotti.application.account.AccountService;
import ca.ulaval.glo4003.trotti.domain.account.fixture.AccountFixture;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AuthentificationControllerTest {

    private AccountService service;
    private AuthentificationController controller;
    private LoginRequest request;

    @BeforeEach
    void setup() {
        service = Mockito.mock(AccountService.class);
        controller = new AuthentificationController(service);
        request = buildValidLoginRequest();
        Mockito.when(service.login(request.email(), request.password()))
                .thenReturn(AccountFixture.AN_AUTH_TOKEN);
    }

    @Test
    void givenValidCredentials_whenLogin_thenReturnOkResponse() {
        Response response = controller.login(request);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void givenValidCredentials_whenLogin_thenReturnLoginResponseWithToken() {
        Response response = controller.login(request);
        LoginResponse loginResponse = (LoginResponse) response.getEntity();

        Assertions.assertEquals(AccountFixture.AN_AUTH_TOKEN.toString(), loginResponse.token());
    }

    @Test
    void givenValidCredentials_whenLogin_thenServiceIsCalled() {
        controller.login(request);

        Mockito.verify(service).login(request.email(), request.password());
    }

    private LoginRequest buildValidLoginRequest() {
        return new LoginRequest(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
    }
}
