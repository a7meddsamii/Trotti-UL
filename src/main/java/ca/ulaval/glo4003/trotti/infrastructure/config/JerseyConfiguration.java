package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.loaders.ServerCompositionRoot;
import ca.ulaval.glo4003.trotti.infrastructure.config.scheduler.ServerLifeCycleListener;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;

@OpenAPIDefinition(
        info = @Info(title = "TrottiUL API", version = "2.0",
                description = "API Documentation for TrottiUL Application",
                contact = @Contact(name = "TrottiUL - Team 10", email = "trotti.ul.10@gmail.com")),
        servers = {@Server(url = "http://localhost:8080/api", description = "Development Server")})
public class JerseyConfiguration extends ResourceConfig {
    private static final String BASE_PACKAGE = "ca.ulaval.glo4003.trotti.api";

    public JerseyConfiguration() {
        ServerCompositionRoot.getInstance().initiate();

        register(RestServerConfiguration.class);
        register(ServerLifeCycleListener.class);
        register(ValidationFeature.class);
        register(JacksonFeature.class);
        register(OpenApiResource.class);
        packages(BASE_PACKAGE);
    }
}
