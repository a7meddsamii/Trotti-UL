package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler.jobs;

import ca.ulaval.glo4003.trotti.application.trip.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class RidePermitActivationJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(RidePermitActivationJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        try {
            RidePermitActivationApplicationService service = ServerResourceLocator.getInstance()
                    .resolve(RidePermitActivationApplicationService.class);
            service.updateActivatedRidePermits();
            LOGGER.info("RidePermitActivationJob executed");
        } catch (Exception e) {
            LOGGER.error("RidePermitActivationJob failed", e);
            throw new JobExecutionException(e, false);
        }
    }
}
