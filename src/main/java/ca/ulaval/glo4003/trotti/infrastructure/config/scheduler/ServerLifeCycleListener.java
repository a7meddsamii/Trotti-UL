package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ServerLifeCycleListener implements ApplicationEventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerLifeCycleListener.class);
	
	@Override
	public void onEvent(ApplicationEvent applicationEvent) {
		switch (applicationEvent.getType()) {
			case INITIALIZATION_FINISHED:
				LOGGER.info("Server resources initialized - starting scheduled jobs");
				ServerResourceLocator.getInstance().register(Scheduler.class, new Scheduler());
			case DESTROY_FINISHED:
				LOGGER.info("Server is shutting down - stopping scheduled jobs");
				Scheduler jobScheduler = ServerResourceLocator.getInstance().resolve(Scheduler.class);
				if (jobScheduler != null) {
					jobScheduler.shutdown();
				}
		}
		
	}
	
	@Override
	public RequestEventListener onRequest(RequestEvent requestEvent) {
		return null;
	}
	
	private List<Job> buildJobs() {
		// TODO: Add scheduled jobs here
		Job exampleJob = new Job("ExampleJob", () -> LOGGER.info("Example job executed"));
		return List.of(exampleJob);
	}
}
