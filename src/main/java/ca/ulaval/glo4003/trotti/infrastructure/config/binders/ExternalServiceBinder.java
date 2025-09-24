package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.application.account.AccountService;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.infrastructure.authentication.argon2.Argon2PasswordHasherAdapter;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import java.time.Clock;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ExternalServiceBinder extends AbstractBinder {

    @Override
    protected void configure() {
        ServerResourceLocator locator = ServerResourceLocator.getInstance();
        bind(locator.resolve(AuthenticationService.class)).to(AuthenticationService.class);

        bind(Clock.systemUTC()).to(Clock.class);
        bind(AccountFactory.class).to(AccountFactory.class);
        bind(Argon2PasswordHasherAdapter.class).to(PasswordHasher.class);
        bind(AccountMapper.class).to(AccountMapper.class);
        bind(AccountService.class).to(AccountService.class);
        // bind(InMemoryAccountRepository.class).to(AccountRepository.class);

    }
}
