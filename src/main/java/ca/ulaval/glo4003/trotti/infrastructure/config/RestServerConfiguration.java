package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.binders.ExternalServiceBinder;
import ca.ulaval.glo4003.trotti.infrastructure.config.binders.ServerResourceInstantiation;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RestServerConfiguration extends AbstractBinder {

    @Override
    protected void configure() {
        ServerResourceInstantiation.getInstance().initiate();
        install(new ExternalServiceBinder());
    }
}
