package ca.ulaval.glo4003.trotti.heartbeat.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.loaders.Bootstrapper;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatController;
import ca.ulaval.glo4003.trotti.heartbeat.api.controllers.HeartbeatResource;

public class HeartbeatResourceLoader extends Bootstrapper {
	
	@Override
	public void load() {
		this.resourceLocator.register(HeartbeatResource.class, new HeartbeatController());
	}
}
