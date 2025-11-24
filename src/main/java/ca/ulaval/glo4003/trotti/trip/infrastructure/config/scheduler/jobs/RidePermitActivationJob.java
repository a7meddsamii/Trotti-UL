package ca.ulaval.glo4003.trotti.trip.infrastructure.config.scheduler.jobs;

import ca.ulaval.glo4003.trotti.billing.application.ridepermit.RidePermitActivationApplicationService;
import ca.ulaval.glo4003.trotti.config.locator.ComponentLocator;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class RidePermitActivationJob implements Job {
    @Override
    public void execute(JobExecutionContext ctx) {
        try {
            RidePermitActivationApplicationService service = ComponentLocator.getInstance()
                    .resolve(RidePermitActivationApplicationService.class);
            service.activateRidePermit();
			service.deactivateRidePermit();
        } catch (Exception ignored) {}
    }
}