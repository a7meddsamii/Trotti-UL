package ca.ulaval.glo4003.trotti.api.account.resources;

import ca.ulaval.glo4003.trotti.api.account.dto.request.LoginRequest;
import ca.ulaval.glo4003.trotti.api.account.dto.response.LoginResponse;
import ca.ulaval.glo4003.trotti.api.account.resources.AuthenticationResource;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.fixtures.AccountFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AuthenticationResourceTest {

    private AccountApplicationService accountApplicationService;
    private LoginRequest request;
    private AuthenticationToken expectedToken;

    private AuthenticationResource authenticationResource;

    @BeforeEach
    void setup() {
        accountApplicationService = Mockito.mock(AccountApplicationService.class);
        request = new LoginRequest(AccountFixture.AN_EMAIL_STRING, AccountFixture.A_RAW_PASSWORD);
        expectedToken = AuthenticationToken.from("jwt-token");
        Mockito.when(
                accountApplicationService.login(Email.from(request.email()), request.password()))
                .thenReturn(expectedToken);

        authenticationResource = new AuthenticationResource(accountApplicationService);
    }

    @Test
    void givenValidLoginRequest_whenLogin_thenReturnsLoginResponseWithToken() {
        LoginResponse response = authenticationResource.login(request);

        Assertions.assertEquals(expectedToken.toString(), response.token());
    }

    @Test
    void givenValidLoginRequest_whenLogin_thenServiceIsCalledWithEmailAndPassword() {
        authenticationResource.login(request);

        Mockito.verify(accountApplicationService).login(Email.from(request.email()),
                request.password());
    }
}
