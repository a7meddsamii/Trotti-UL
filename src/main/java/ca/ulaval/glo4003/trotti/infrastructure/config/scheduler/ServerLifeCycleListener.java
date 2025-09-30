package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import ca.ulaval.glo4003.trotti.application.trip.ActivationNotificationService;
import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;
import java.time.Duration;
import java.util.List;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerLifeCycleListener implements ApplicationEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerLifeCycleListener.class);

    @Override
    public void onEvent(ApplicationEvent applicationEvent) {
        switch (applicationEvent.getType()) {
            case INITIALIZATION_FINISHED:
                LOGGER.info("Server resources initialized - setting up scheduled jobs");
                ServerResourceLocator.getInstance().register(Scheduler.class, new Scheduler());
                setupJobs();
                break;
            case DESTROY_FINISHED:
                LOGGER.info("Server is shutting down - destroying scheduled jobs");
                Scheduler jobScheduler =
                        ServerResourceLocator.getInstance().resolve(Scheduler.class);
                if (jobScheduler != null) {
                    jobScheduler.shutdown();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }

    private void setupJobs() {
        Scheduler jobScheduler = ServerResourceLocator.getInstance().resolve(Scheduler.class);
        List<Job> jobs = buildJobs();
        jobScheduler.scheduleAtFixedRate(Duration.ofSeconds(5), Duration.ofHours(12),
                jobs.toArray(new Job[0]));
    }

    private List<Job> buildJobs() {
        ActivationNotificationService service =
                ServerResourceLocator.getInstance().resolve(ActivationNotificationService.class);
        Job activationNotificationService =
                new Job("Activation Notification Service", service::updateTravelersPermits);
        return List.of(activationNotificationService);
    }
}
