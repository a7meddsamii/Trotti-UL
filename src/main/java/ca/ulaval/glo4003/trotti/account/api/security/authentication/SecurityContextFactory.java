package ca.ulaval.glo4003.trotti.account.api.security.authentication;

import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticatedIdentity;
import jakarta.ws.rs.core.SecurityContext;

public interface SecurityContextFactory {
    SecurityContext create(AuthenticatedIdentity identity, boolean secure);
}
