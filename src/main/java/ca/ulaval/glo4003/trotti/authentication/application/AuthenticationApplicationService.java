package ca.ulaval.glo4003.trotti.authentication.application;

import ca.ulaval.glo4003.trotti.authentication.application.dto.LoginInfo;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.authentication.domain.gateway.IdentityGateway;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenGenerator;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;

public class AuthenticationApplicationService implements AuthenticationService {
    private final SessionTokenGenerator sessionTokenGenerator;
    private final IdentityGateway identityGateway;

    public AuthenticationApplicationService(
            SessionTokenGenerator sessionTokenGenerator,
            IdentityGateway identityGateway) {
        this.sessionTokenGenerator = sessionTokenGenerator;
        this.identityGateway = identityGateway;
    }

    public SessionToken login(LoginInfo loginInfo) {
        Identity identity = identityGateway.findByEmail(loginInfo.email());
        identity.verifyPassword(loginInfo.rawPassword());

        return sessionTokenGenerator.generateToken(identity.getIdul(), identity.getRole(),
												   identity.getPermissions());
    }
}
