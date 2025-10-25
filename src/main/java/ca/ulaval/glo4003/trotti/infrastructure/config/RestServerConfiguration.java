package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.binders.ApplicationBinder;
import ca.ulaval.glo4003.trotti.infrastructure.config.loaders.ServerCompositionRoot;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RestServerConfiguration extends AbstractBinder {

    @Override
    protected void configure() {
        ServerCompositionRoot.getInstance().initiate();
        install(new ApplicationBinder());
    }
}
