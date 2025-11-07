package ca.ulaval.glo4003.trotti.authentication.application;

import ca.ulaval.glo4003.trotti.authentication.application.dto.LoginInfo;
import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.authentication.domain.gateway.IdentityGateway;
import ca.ulaval.glo4003.trotti.authentication.domain.services.SessionTokenService;
import ca.ulaval.glo4003.trotti.authentication.domain.values.SessionToken;

public class AuthenticationApplicationService {
	SessionTokenService sessionTokenService;
	IdentityGateway identityGateway;
	
	public AuthenticationApplicationService(SessionTokenService sessionTokenService, IdentityGateway identityGateway) {
		this.sessionTokenService = sessionTokenService;
		this.identityGateway = identityGateway;
	}
	
	public SessionToken login(LoginInfo loginInfo) {
		Identity identity = identityGateway.findByEmail(loginInfo.email());
		identity.verifyPassword(loginInfo.rawPassword());
		
		return sessionTokenService.generateToken(identity.getIdul(), identity.getRole(), identity.getPermissions());
	}
}
