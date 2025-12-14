package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.LoginRequest;
import ca.ulaval.glo4003.trotti.account.api.dto.LoginResponse;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.account.fixtures.AccountFixture;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AuthenticationControllerTest {

    private AccountApplicationService accountApplicationService;
    private LoginRequest request;
    private SessionToken expectedToken;

    private AuthenticationResource authenticationController;

    @BeforeEach
    void setup() {
        accountApplicationService = Mockito.mock(AccountApplicationService.class);
        request = new LoginRequest(AccountFixture.EMAIL_STRING, AccountFixture.RAW_PASSWORD);
        expectedToken = SessionToken.from("jwt-token");
        LoginDto loginDto = new LoginDto(Email.from(request.email()), request.password());
        Mockito.when(accountApplicationService.login(loginDto)).thenReturn(expectedToken);

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

        LoginDto loginDto = new LoginDto(Email.from(request.email()), request.password());
        Mockito.verify(accountApplicationService).login(loginDto);
    }
}
