package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.LoginRequest;
import ca.ulaval.glo4003.trotti.account.api.dto.LoginResponse;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.InvalidParameterException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

class AuthenticationControllerTest {

    private static final String VALID_EMAIL = "john.doe@ulaval.ca";
    private static final String VALID_PASSWORD = "StrongPass1!";
    private static final SessionToken SESSION_TOKEN = SessionToken.from("jwt-token");

    private AccountApplicationService accountApplicationService;
    private LoginRequest request;

    private AuthenticationResource authenticationController;

    @BeforeEach
    void setup() {
        accountApplicationService = Mockito.mock(AccountApplicationService.class);
        request = new LoginRequest(VALID_EMAIL, VALID_PASSWORD);
        LoginDto loginDto = new LoginDto(Email.from(VALID_EMAIL), VALID_PASSWORD);

        Mockito.when(accountApplicationService.login(loginDto)).thenReturn(SESSION_TOKEN);

        authenticationController = new AuthenticationController(accountApplicationService);
    }

    @Test
    void givenValidLoginRequest_whenLogin_thenReturnsLoginResponseWithToken() {
        Response response = authenticationController.login(request);

        LoginResponse expectedResponse = new LoginResponse(SESSION_TOKEN);
        Assertions.assertEquals(expectedResponse, response.getEntity());
    }

    @Test
    void givenValidLoginRequest_whenLogin_thenServiceIsCalledWithEmailAndPassword() {
        authenticationController.login(request);

        LoginDto loginDto = new LoginDto(Email.from(VALID_EMAIL), VALID_PASSWORD);
        Mockito.verify(accountApplicationService).login(loginDto);
    }

    @Test
    void givenNullRequest_whenLogin_thenThrowsException() {
        Executable loginAttempt = () -> authenticationController.login(null);

        Assertions.assertThrows(InvalidParameterException.class, loginAttempt);
    }
}
