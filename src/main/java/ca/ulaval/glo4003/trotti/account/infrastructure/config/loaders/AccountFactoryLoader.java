package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.time.Clock;

public class AccountFactoryLoader extends Bootstrapper {

    @Override
    public void load() {
        this.loadFactory();
    }

    private void loadFactory() {
        Clock clock = this.resourceLocator.resolve(Clock.class);
        this.resourceLocator.register(AccountFactory.class, new AccountFactory(clock));
    }
}
