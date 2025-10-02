package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerManager.class);

    private final Scheduler scheduler;

    public SchedulerManager() {
        try {
            this.scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to create quartz scheduler", e);
        }
    }

    public void start() {
        try {
            if (!scheduler.isStarted())
                scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to start scheduler", e);
        }
    }

    public void shutdown() {
        try {
            if (!scheduler.isShutdown())
                scheduler.shutdown(true);
        } catch (SchedulerException e) {
            LOGGER.warn("Error shutting down scheduler", e);
        }
    }

    public boolean scheduleIfAbsent(JobDetail jobDetail, Trigger trigger)
            throws SchedulerException {
        TriggerKey triggerKey = trigger.getKey();
        if (scheduler.checkExists(triggerKey)) {
            LOGGER.info("Trigger {} already exists, skipping schedule", triggerKey);
            return false;
        }

        JobKey jobKey = jobDetail.getKey();
        if (!scheduler.checkExists(jobKey)) {
            scheduler.addJob(jobDetail, false);
        }

        scheduler.scheduleJob(trigger);
        LOGGER.info("Scheduled job {} with trigger {}", jobDetail.getKey(), trigger.getKey());
        return true;
    }
}
