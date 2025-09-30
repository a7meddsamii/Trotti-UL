package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;

public class ServerLifeCycleListener implements ApplicationEventListener {
	public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ServerLifeCycleListener.class);
	
	private Scheduler scheduler;
	
	@Override
	public void onEvent(ApplicationEvent applicationEvent) {
		switch (applicationEvent.getType()) {
			case INITIALIZATION_FINISHED:
				scheduleJobs();
				break;
			case DESTROY_FINISHED:
				shutdownScheduler();
				break;
			default:
				break;
		}
	}
	
	@Override
	public RequestEventListener onRequest(RequestEvent requestEvent) {
		return null;
	}
	
	private void scheduleJobs() {
		try {
			
			JobDetail job = JobBuilder.newJob(TrottiServerJob.class)
					.storeDurably()
					.withIdentity("activationNotificationJob", "tripJobs")
					.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("activationTrigger", "tripJobs")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0,12 * * ?"))
					.build();
			
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
			
		} catch (SchedulerException e) {
			LOGGER.error("Failed to schedule ride permit activation service", e);
		}
	}
	
	private void shutdownScheduler() {
		if (scheduler != null) {
			try {
				scheduler.shutdown(true);
			} catch (SchedulerException e) {
				LOGGER.error("Failed to shutdown scheduler", e);
			}
		}
	}
}

