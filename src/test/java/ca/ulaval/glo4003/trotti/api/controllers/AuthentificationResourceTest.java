package ca.ulaval.glo4003.trotti.api.controllers;

import ca.ulaval.glo4003.trotti.api.dto.requests.LoginRequest;
import ca.ulaval.glo4003.trotti.api.dto.responses.LoginResponse;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AuthentificationResourceTest {

    private AccountApplicationService accountApplicationService;
    private LoginRequest request;
    private AuthenticationToken expectedToken;

    private AuthentificationResource authentificationResource;

    @BeforeEach
    void setup() {
        accountApplicationService = Mockito.mock(AccountApplicationService.class);
        request = new LoginRequest(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
        expectedToken = AuthenticationToken.from("jwt-token");
        Mockito.when(
                accountApplicationService.login(Email.from(request.email()), request.password()))
                .thenReturn(expectedToken);

        authentificationResource = new AuthentificationResource(accountApplicationService);
    }

    @Test
    void givenValidLoginRequest_whenLogin_thenReturnsLoginResponseWithToken() {
        LoginResponse response = authentificationResource.login(request);

        Assertions.assertEquals(expectedToken.toString(), response.token());
    }

    @Test
    void givenValidLoginRequest_whenLogin_thenServiceIsCalledWithEmailAndPassword() {
        authentificationResource.login(request);

        Mockito.verify(accountApplicationService).login(Email.from(request.email()),
                request.password());
    }
}
