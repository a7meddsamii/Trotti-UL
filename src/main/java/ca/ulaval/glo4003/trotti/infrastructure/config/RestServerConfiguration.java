package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.binders.ApplicationServiceBinder;
import ca.ulaval.glo4003.trotti.infrastructure.config.binders.ServerResourceInstantiator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RestServerConfiguration extends AbstractBinder {

    @Override
    protected void configure() {
        ServerResourceInstantiator.getInstance().initiate();
        install(new ApplicationServiceBinder());
        // Add more binders as needed here
    }
}
