package ca.ulaval.glo4003.trotti.authentication.domain.gateway;

import ca.ulaval.glo4003.trotti.authentication.domain.entities.Identity;
import ca.ulaval.glo4003.trotti.commons.domain.values.Email;

public interface IdentityGateway {
	
	Identity findByEmail(Email email);
}
