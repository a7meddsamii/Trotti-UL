package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.api.security.authentication.AuthenticationFilter;
import ca.ulaval.glo4003.trotti.account.api.security.authentication.SecurityContextFactory;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class AccountApiFilterLoader extends Bootstrapper {

    @Override
    public void load() {
        SessionTokenProvider sessionTokenProvider = this.resourceLocator.resolve(SessionTokenProvider.class);
        SecurityContextFactory securityContextFactory = this.resourceLocator.resolve(SecurityContextFactory.class);

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(sessionTokenProvider, securityContextFactory);

        this.resourceLocator.register(AuthenticationFilter.class, authenticationFilter);
    }
}
