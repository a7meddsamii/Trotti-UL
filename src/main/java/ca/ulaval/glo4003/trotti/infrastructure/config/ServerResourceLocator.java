package ca.ulaval.glo4003.trotti.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

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

    public <T> T resolve(Class<T> serviceClass) {
        return serviceClass.cast(services.get(serviceClass));
    }
}
