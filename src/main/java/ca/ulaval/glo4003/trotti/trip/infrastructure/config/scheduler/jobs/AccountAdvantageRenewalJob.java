package ca.ulaval.glo4003.trotti.trip.infrastructure.config.scheduler.jobs;

import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.domain.order.values.Session;
import ca.ulaval.glo4003.trotti.commons.domain.events.EventBus;
import ca.ulaval.glo4003.trotti.commons.domain.events.account.AccountAdvantageRenewalEvent;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;
import ca.ulaval.glo4003.trotti.config.locator.ComponentLocator;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;

import java.time.Clock;
import java.time.LocalDate;

@DisallowConcurrentExecution
public class AccountAdvantageRenewalJob implements Job {
	public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AccountAdvantageRenewalJob.class); 
	private Session currentSession;
	
	@Override
	public void execute(JobExecutionContext ctx) {
		try {
			if(isSessionChanged()) {
				EventBus eventBus = ComponentLocator.getInstance()
						.resolve(EventBus.class);
				
				eventBus.publish(new AccountAdvantageRenewalEvent("FREE_RIDE_PERMIT"));
			}
			
		} catch (Exception exception) {
			LOGGER.error("Ride permit renewal job failed : ", exception);
		}
	}
	
	private boolean isSessionChanged(){
		Clock clock = ComponentLocator.getInstance().resolve(Clock.class);
		LocalDate today = LocalDate.now(clock);
		
		SchoolSessionProvider schoolSessionProvider = ComponentLocator.getInstance()
				.resolve(SchoolSessionProvider.class);
		Session session = schoolSessionProvider.getSession(today)
				.orElseThrow(() -> new NotFoundException("No Session found for date " + today));
		
		if(session.equals(currentSession)){
			return false;
		}
		
		currentSession = session;
		return true;
	}
}
