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
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.LocalDate;

@DisallowConcurrentExecution
public class AccountAdvantageRenewalJob implements Job {

    public static final Logger LOGGER =
            LoggerFactory.getLogger(AccountAdvantageRenewalJob.class);

    private static volatile Session currentSession;
    
    private static boolean isInitialized = false;

    @Override
    public void execute(JobExecutionContext ctx) {
        try {
            if (!isInitialized) {
                currentSession = getSession();
                isInitialized = true;
            }
            if (isSessionChanged()) {
                EventBus eventBus = ComponentLocator.getInstance().resolve(EventBus.class);
                eventBus.publish(new AccountAdvantageRenewalEvent("FREE_RIDE_PERMIT"));
                LOGGER.info("Ride permit renewal event published.");
            }
        } catch (Exception exception) {
            LOGGER.error("Ride permit renewal job failed:", exception);
        }
    }

    private static boolean isSessionChanged() {
        Session newSession = getSession();

        if (newSession.equals(currentSession)) {
            return false;
        }

        currentSession = newSession;
        LOGGER.info("Session has changed to: {}", newSession);
        return true;
    }

    private static Session getSession() {
        Clock clock = ComponentLocator.getInstance().resolve(Clock.class);
        LocalDate today = LocalDate.now(clock);

        SchoolSessionProvider schoolSessionProvider =
                ComponentLocator.getInstance().resolve(SchoolSessionProvider.class);

        return schoolSessionProvider.getSession(today)
                .orElseThrow(() -> new NotFoundException("No Session found for date " + today));
    }
}
