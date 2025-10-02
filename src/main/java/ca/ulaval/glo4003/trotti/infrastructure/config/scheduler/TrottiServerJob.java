package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrottiServerJob implements Job{
    private static final Logger LOGGER = LoggerFactory.getLogger(TrottiServerJob.class);
    private final Runnable task;

    public TrottiServerJob(Runnable task) {
        this.task = task;
    }
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		String name = jobExecutionContext.getJobDetail().getKey().getName();
		
		try {
			LOGGER.info("Executing scheduled job: {}", name);
			task.run();
		} catch (Exception e) {
			LOGGER.error("Error occurred during execution of scheduled job: {}", name, e);
		}
	}
}
