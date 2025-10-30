package ca.ulaval.glo4003.trotti.api.authentication.controllers;

import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationController;
import ca.ulaval.glo4003.trotti.account.api.controllers.AuthenticationResource;
import ca.ulaval.glo4003.trotti.account.api.dto.LoginRequest;
import ca.ulaval.glo4003.trotti.account.api.dto.LoginResponse;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AuthenticationControllerTest {

    private AccountApplicationService accountApplicationService;
    private LoginRequest request;
    private AuthenticationToken expectedToken;

    private AuthenticationResource authenticationController;

    @BeforeEach
    void setup() {
        accountApplicationService = Mockito.mock(AccountApplicationService.class);
        request = new LoginRequest(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
        expectedToken = AuthenticationToken.from("jwt-token");
        Mockito.when(
                accountApplicationService.login(Email.from(request.email()), request.password()))
                .thenReturn(expectedToken);

        authenticationController = new AuthenticationController(accountApplicationService);
    }

    @Test
    void givenValidLoginRequest_whenLogin_thenReturnsLoginResponseWithToken() {
        Response response = authenticationController.login(request);

        LoginResponse expectedResponse = new LoginResponse(expectedToken);
        Assertions.assertEquals(expectedResponse, response.getEntity());
    }

    @Test
    void givenValidLoginRequest_whenLogin_thenServiceIsCalledWithEmailAndPassword() {
        authenticationController.login(request);

        Mockito.verify(accountApplicationService).login(Email.from(request.email()),
                request.password());
    }
}
