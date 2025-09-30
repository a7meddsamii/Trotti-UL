package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import java.time.Duration;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrottiServerJob implements Job{
    private static final Logger LOGGER = LoggerFactory.getLogger(TrottiServerJob.class);
    private final String name;
    private final Runnable task;

    public TrottiServerJob(String name, Runnable task, Duration initialDelay, Duration period) {
        this.name = name;
        this.task = task;
    }
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			LOGGER.info("Executing scheduled job: {}", name);
			task.run();
		} catch (Exception e) {
			LOGGER.error("Error occurred during execution of scheduled job: {}", name, e);
		}
	}
}
