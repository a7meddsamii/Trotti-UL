package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.binders.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RestServerConfiguration extends AbstractBinder {

    @Override
    protected void configure() {
        ServerResourceInstantiation.getInstance().initiate();
        install(new ApplicationBinder());
    }
}
