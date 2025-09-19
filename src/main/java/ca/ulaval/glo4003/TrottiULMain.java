package ca.ulaval.glo4003;

import ca.ulaval.glo4003.trotti.api.CORSResponseFilter;
import ca.ulaval.glo4003.trotti.infrastructure.config.RestServerConfiguration;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class TrottiULMain {
	private static final Logger LOGGER = LoggerFactory.getLogger(TrottiULMain.class);
	public static final String BASE_URI = "http://localhost:8080/";
	
	
	public static void main(String[] args) {
		LOGGER.info("Setup resources (API)");
		final ResourceConfig config = new ResourceConfig();
		config.register(RestServerConfiguration.class);
		config.register(CORSResponseFilter.class);
		config.packages("ca.ulaval.glo4003.trotti.api");
		
		try {
			LOGGER.info("Setup http server");
			final Server server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
			
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				try {
					LOGGER.info("Shutting down the application...");
					server.stop();
					LOGGER.info("Done, exit.");
				} catch (Exception e) {
					LOGGER.error("Error shutting down the server", e);
				} finally {
					server.destroy();
				}
			}));
			
			LOGGER.info("Application started. Stop the application using CTRL+C");
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			LOGGER.error("Error starting up the server", e);
		}
	}
}
