package ca.ulaval.glo4003.trotti.trip.infrastructure.config.scheduler.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
@DisallowConcurrentExecution
public class RidePermitActivationJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(RidePermitActivationJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
        } catch (Exception e) {
            LOGGER.error("RidePermitActivationJob failed", e);
            throw new JobExecutionException(e, false);
        }
    }
}
