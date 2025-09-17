package ca.ulaval.glo4003.trotti.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

public class ServerResourceLocator {
	
	private final Map<Class<?>, Object> services;
	private static ServerResourceLocator singleton;
	
	public static ServerResourceLocator getInstance() {
		if (singleton != null) return singleton;
		return singleton = new ServerResourceLocator();
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
