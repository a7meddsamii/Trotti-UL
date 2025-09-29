package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ResourceBinder extends AbstractBinder {

    @Override
    protected void configure() {
        ServerResourceLocator locator = ServerResourceLocator.getInstance();
        bind(locator.resolve(AuthenticationService.class)).to(AuthenticationService.class);
        bind(locator.resolve(AccountRepository.class)).to(AccountRepository.class);
    }
}