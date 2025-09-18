package ca.ulaval.glo4003.trotti.infrastructure.config.binders;

import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationServiceBinder extends AbstractBinder {
	
	@Override
	protected void configure() {
		ServerResourceLocator locator = ServerResourceLocator.getInstance();
//      Binding examples:
//		1- Binding that is not linked to any interface
//      	bindAsContract(SomeClassImplementation.class);

//		2- Binding linked to an interface
//      	bind(SomeClassImplementation.class).to(SomeInterface.class);

//      3- Binding linked to an interface AND requires some dependency injection
		bind(locator.resolve(DummyClass.class)).to(DummyInterface.class);

//		4-Binding that is NOT linked to an interface BUT requires some dependency injection
//			bind(locator.resolve(SecondDummyClass.class)).to(SecondDummyClass.class);
	}
}