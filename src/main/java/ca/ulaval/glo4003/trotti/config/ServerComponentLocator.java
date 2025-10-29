package ca.ulaval.glo4003.trotti.config;

import ca.ulaval.glo4003.trotti.config.exceptions.ConfigurationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ServerComponentLocator {

    private final Map<Class<?>, Object> services;
    private static ServerComponentLocator instance;

    public static ServerComponentLocator getInstance() {
        if (instance == null) {
            instance = new ServerComponentLocator();
        }

        return instance;
    }

    private ServerComponentLocator() {
        services = new HashMap<>();
    }

    public <T> void register(Class<T> serviceClass, T serviceInstance) {
        services.put(serviceClass, serviceInstance);
    }

    public <T> T resolve(Class<T> serviceClass) throws ConfigurationException {
        Object serviceInstance = services.get(serviceClass);

        if (Optional.ofNullable(serviceInstance).isEmpty()) {
            throw new ConfigurationException(String
                    .format("No server component instance found for %s", serviceClass.getName()));
        }

        return serviceClass.cast(serviceInstance);
    }
}
