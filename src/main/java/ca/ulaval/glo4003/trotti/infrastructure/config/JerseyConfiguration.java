package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.loaders.ServerCompositionRoot;
import ca.ulaval.glo4003.trotti.infrastructure.config.scheduler.ServerLifeCycleListener;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;

public class JerseyConfiguration extends ResourceConfig {
    private static final String BASE_PACKAGE = "ca.ulaval.glo4003.trotti.api";

    public JerseyConfiguration() {
        ServerCompositionRoot.getInstance().initiate();

        register(RestServerConfiguration.class);
        register(ServerLifeCycleListener.class);
        register(ValidationFeature.class);
        register(JacksonFeature.class);
        packages(BASE_PACKAGE);
    }
}
