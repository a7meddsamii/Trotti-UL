package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;

import java.util.List;
import java.util.Objects;

public class ServerResourceInstantiator {
	
	private static ServerResourceInstantiator instance;
	private final ServerResourceLocator locator;
	private boolean resourcesCreated;
	
	public static ServerResourceInstantiator getInstance() {
		if (!Objects.isNull(instance)) {
			return instance;
		}
		
		return instance = new ServerResourceInstantiator();
	}
	
	private ServerResourceInstantiator() {
		this.locator = ServerResourceLocator.getInstance();
		this.resourcesCreated = false;
	}
	
	public void initiate() {
		if (resourcesCreated) {
			return;
		}
		
		// example of a server resource creation requiring a dependency injection
		List<Integer> someIntegers = List.of(1234, 5678);
		DummyClass myServerResource = new DummyClass(someIntegers);
		locator.register(DummyClass.class, myServerResource);
		
		// add more resources as needed here
		
		resourcesCreated = true;
	}
}