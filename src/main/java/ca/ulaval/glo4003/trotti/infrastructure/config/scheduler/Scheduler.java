package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private static final int NUMBER_OF_THREADS = 2;

    private final ScheduledExecutorService executorService;
    private final List<ScheduledFuture<?>> scheduledFutures;

    public Scheduler() {
        this.executorService = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);
        this.scheduledFutures = new ArrayList<>();
    }

    public void scheduleAtFixedRate(Duration initialDelay, Duration period, Job... jobs) {
        for (Runnable job : jobs) {
            ScheduledFuture<?> scheduledFuture =
                    executorService.scheduleAtFixedRate(job, initialDelay.toMillis(),
                            period.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
            scheduledFutures.add(scheduledFuture);
        }
    }

    public void shutdown() {
        scheduledFutures.forEach(scheduledFuture -> {
            try {
                scheduledFuture.cancel(true);
            } catch (Exception e) {
                LOGGER.warn("Failed to cancel scheduled job", e);
            }
        });

        scheduledFutures.clear();
        executorService.shutdownNow();
    }
}
