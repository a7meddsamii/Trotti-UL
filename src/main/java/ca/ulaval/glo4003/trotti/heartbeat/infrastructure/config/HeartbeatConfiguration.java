package ca.ulaval.glo4003.trotti.heartbeat.infrastructure.config;

import ca.ulaval.glo4003.trotti.config.Configuration;
import ca.ulaval.glo4003.trotti.heartbeat.infrastructure.config.loaders.HeartbeatResourceLoader;

public class HeartbeatConfiguration extends Configuration {
	
	private HeartbeatConfiguration() {
		super();
	}
	
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new HeartbeatConfiguration();
		}
		
		return instance;
	}
	
	@Override
	protected void load() {
		new HeartbeatResourceLoader().load();
	}
}
