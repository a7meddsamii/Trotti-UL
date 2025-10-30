package ca.ulaval.glo4003.trotti.config.locator;

import ca.ulaval.glo4003.trotti.config.exceptions.ConfigurationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ComponentLocator {

    private final Map<Class<?>, Object> services;
    private static ComponentLocator instance;

    public static ComponentLocator getInstance() {
        if (instance == null) {
            instance = new ComponentLocator();
        }

        return instance;
    }

    private ComponentLocator() {
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
