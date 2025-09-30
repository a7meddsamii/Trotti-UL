package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.api.AccountApiMapper;
import ca.ulaval.glo4003.trotti.api.controllers.AccountResource;
import ca.ulaval.glo4003.trotti.api.controllers.AuthentificationResource;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.communication.EmailService;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        ServerResourceLocator locator = ServerResourceLocator.getInstance();
        bind(locator.resolve(AuthenticationService.class)).to(AuthenticationService.class);
        bind(locator.resolve(AccountRepository.class)).to(AccountRepository.class);
        bind(locator.resolve(EmailService.class)).to(EmailService.class);
        bind(locator.resolve(PasswordHasher.class)).to(PasswordHasher.class);
        bind(locator.resolve(AccountApplicationService.class)).to(AccountApplicationService.class);
        bind(locator.resolve(AccountApiMapper.class)).to(AccountApiMapper.class);
        bind(locator.resolve(AccountResource.class)).to(AccountResource.class);
        bind(locator.resolve(AuthentificationResource.class)).to(AuthentificationResource.class);
    }
}
