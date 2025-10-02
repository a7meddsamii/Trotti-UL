package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import ca.ulaval.glo4003.trotti.infrastructure.config.scheduler.jobs.RidePermitActivationJob;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.quartz.*;
import org.slf4j.Logger;

public class ServerLifeCycleListener implements ApplicationEventListener {
	public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ServerLifeCycleListener.class);
	public static final String EVERYDAY_AT_MIDNIGHT_AND_NOON = "0/5 * * * * ?";
	
	private final SchedulerManager schedulerManager;
	
	public ServerLifeCycleListener() {
		this.schedulerManager = new SchedulerManager();
	}
	
	@Override
	public void onEvent(ApplicationEvent applicationEvent) {
		switch (applicationEvent.getType()) {
			case INITIALIZATION_FINISHED:
				scheduleRidePermitActivationJob();
				break;
			case DESTROY_FINISHED:
				this.schedulerManager.shutdown();
				break;
			default:
				break;
		}
	}
	
	@Override
	public RequestEventListener onRequest(RequestEvent requestEvent) {
		return null;
	}
	
	private void scheduleRidePermitActivationJob() {
		String name = RidePermitActivationJob.class.getSimpleName();
		String group =  "tripJobs";
		JobKey jobKey = new JobKey(name, group);
		
		try {
			JobDetail jobDetail = buildJobDetail(RidePermitActivationJob.class, name, group);
			Trigger trigger = buildTrigger(EVERYDAY_AT_MIDNIGHT_AND_NOON, name, group, jobKey);
			scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			LOGGER.error("Failed to schedule ride permit activation service", e);
		}
	}
	
	private JobDetail buildJobDetail(Class<? extends Job> jobClass, String jobName, String jobGroup) {
		return JobBuilder.newJob(jobClass)
				.withIdentity(jobName, jobGroup)
				.storeDurably()
				.build();
	}
	
	private Trigger buildTrigger(String cronExpression, String triggerName, String triggerGroup, JobKey jobKey) {
		return TriggerBuilder.newTrigger()
				.withIdentity(triggerName + "Trigger", triggerGroup)
				.forJob(jobKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
	}
	
	private void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
		schedulerManager.start();
		schedulerManager.scheduleIfAbsent(jobDetail, trigger);
	}
}

