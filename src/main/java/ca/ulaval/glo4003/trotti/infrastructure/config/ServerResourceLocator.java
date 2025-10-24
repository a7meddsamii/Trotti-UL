package ca.ulaval.glo4003.trotti.infrastructure.config;

import ca.ulaval.glo4003.trotti.infrastructure.config.errors.ConfigurationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServerResourceLocator {

    private final Map<Class<?>, Object> services;
    private static ServerResourceLocator instance;

    public static ServerResourceLocator getInstance() {
        if (instance == null) {
            instance = new ServerResourceLocator();
        }

        return instance;
    }

    private ServerResourceLocator() {
        services = new HashMap<>();
    }

    public <T> void register(Class<T> serviceClass, T serviceInstance) {
        services.put(serviceClass, serviceInstance);
    }

    public <T> T resolve(Class<T> serviceClass) throws ConfigurationException {
		Object serviceInstance = services.get(serviceClass);
		
		if(Optional.ofNullable(serviceInstance).isEmpty()) {
			throw new ConfigurationException(String.format("No server resource instance found for %s", serviceClass.getName()));
		}
		
        return serviceClass.cast(serviceInstance);
    }
}
