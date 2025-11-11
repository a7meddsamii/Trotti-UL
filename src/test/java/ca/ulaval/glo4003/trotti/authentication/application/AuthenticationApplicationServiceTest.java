package ca.ulaval.glo4003.trotti.authentication.application;

import ca.ulaval.glo4003.trotti.authentication.application.dto.LoginInfo;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.authentication.domain.gateway.IdentityGateway;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenGenerator;
import ca.ulaval.glo4003.trotti.commons.domain.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationApplicationServiceTest {
    private static final Email AN_EMAIL = Email.from("an_email@ulaval.ca");
    private static final LoginInfo LOGIN_INFO = new LoginInfo(AN_EMAIL, "aRawPassword");

    private Identity identity;
    private SessionTokenGenerator sessionTokenGenerator;
    private IdentityGateway identityGateway;

    private AuthenticationService authenticationApplicationService;

    @BeforeEach
    void setup() {
        identity = Mockito.mock(Identity.class);
        sessionTokenGenerator = Mockito.mock(SessionTokenGenerator.class);
        identityGateway = Mockito.mock(IdentityGateway.class);
        authenticationApplicationService =
                new AuthenticationApplicationService(sessionTokenGenerator, identityGateway);
    }

    @Test
    void givenLoginInfo_whenLogin_thenShouldVerifyPassword() {
        Mockito.when(identityGateway.findByEmail(AN_EMAIL)).thenReturn(identity);

        authenticationApplicationService.login(LOGIN_INFO);

        Mockito.verify(identity).verifyPassword(LOGIN_INFO.rawPassword());
    }

    @Test
    void givenLoginInfo_whenLogin_thenShouldGenerateToken() {
        Mockito.when(identityGateway.findByEmail(AN_EMAIL)).thenReturn(identity);

        authenticationApplicationService.login(LOGIN_INFO);

        Mockito.verify(sessionTokenGenerator).generateToken(identity.getIdul(), identity.getRole(),
															identity.getPermissions());
    }
}
