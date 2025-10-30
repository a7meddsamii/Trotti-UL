package ca.ulaval.glo4003.trotti.config;

import ca.ulaval.glo4003.trotti.trip.infrastructure.config.scheduler.ServerLifeCycleListener;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;

@OpenAPIDefinition(info = @Info(title = "Projet Trotti-UL - REST API", version = "2.0",
        description = "Service de l’Université Laval de location de trottinettes électriques pour une locomotion optimale",
        contact = @Contact(name = "TrottiUL - Equipe 10", email = "trotti.ul.10@gmail.com")),
        servers = {@Server(url = "http://localhost:8080/api",
                description = "Serveur de développement")},
        security = {@SecurityRequirement(name = "auth")})
@SecurityScheme(name = "auth", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
        paramName = "Authorization")
public class JerseyConfiguration extends ResourceConfig {
    private static final String BASE_PACKAGE = "ca.ulaval.glo4003.trotti.api";

    public JerseyConfiguration() {
        ApplicationContext.getInstance().initiate();
        register(RestServerBinder.class);
        register(ServerLifeCycleListener.class);
        register(ValidationFeature.class);
        register(JacksonFeature.class);
        register(OpenApiResource.class);
        packages(BASE_PACKAGE);
    }
}
