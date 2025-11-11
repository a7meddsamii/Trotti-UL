package ca.ulaval.glo4003.trotti.authentication.infrastructure.config;

import ca.ulaval.glo4003.trotti.authentication.infrastructure.config.loaders.*;
import ca.ulaval.glo4003.trotti.config.Configuration;

public class AuthenticationConfiguration extends Configuration {

    protected static Configuration instance;

    private AuthenticationConfiguration() {
        super();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new AuthenticationConfiguration();
        }

        return instance;
    }

    @Override
    protected void load() {
		new AuthenticationForeignServiceLoader().load();
        new AuthenticationFactoryLoader().load();
        new AuthenticationMapperLoader().load();
		new AuthenticationGatewayLoader().load();
        new AuthenticationApplicationServiceLoader().load();
		new AuthenticationFilterLoader().load();
    }
}
