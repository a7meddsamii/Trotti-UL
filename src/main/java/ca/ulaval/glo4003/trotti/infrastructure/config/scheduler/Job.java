package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);
    private final String name;
    private final Runnable task;
    private final Duration initialDelay;
    private final Duration period;

    public Job(String name, Runnable task, Duration initialDelay, Duration period) {
        this.name = name;
        this.task = task;
        this.initialDelay = initialDelay;
        this.period = period;
    }

    public Duration getInitialDelay() {
        return initialDelay;
    }

    public Duration getPeriod() {
        return period;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("Executing scheduled job: {}", name);
            task.run();
        } catch (Exception e) {
            LOGGER.error("Error occurred during execution of scheduled job: {}", name, e);
        }
    }
}
