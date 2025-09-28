package ca.ulaval.glo4003.trotti.infrastructure.config.scheduler;

import ca.ulaval.glo4003.trotti.infrastructure.config.ServerResourceLocator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Scheduler {
    private static final int NUMBER_OF_THREADS = 2;
    private final ScheduledExecutorService executorService;
    private final List<ScheduledFuture<?>> scheduledFutures;

    public Scheduler() {
        this.executorService = Executors.newScheduledThreadPool(NUMBER_OF_THREADS);
        this.scheduledFutures = new ArrayList<>();
		ServerResourceLocator.getInstance().register(Scheduler.class, this);
    }

    public void scheduleAtFixedRate(Duration initialDelay, Duration period, Runnable... jobs) {
        for (Runnable job : jobs) {
            ScheduledFuture<?> scheduledFuture =
                    executorService.scheduleAtFixedRate(job, initialDelay.toMillis(),
                            period.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
            scheduledFutures.add(scheduledFuture);
        }
    }
	
	public void shutdown() {
		scheduledFutures.forEach(scheduledFuture -> scheduledFuture.cancel(true));
		executorService.shutdownNow();
	}
}
