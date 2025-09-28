package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Job implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Job.class);
    private final String name;
    private final Runnable task;

    public Job(String name, Runnable task) {
        this.name = name;
        this.task = task;
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
