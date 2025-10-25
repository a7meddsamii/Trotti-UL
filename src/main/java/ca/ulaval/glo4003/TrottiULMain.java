package ca.ulaval.glo4003;

import ca.ulaval.glo4003.trotti.infrastructure.config.JerseyConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrottiULMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrottiULMain.class);

    private static final int PORT = 8080;

    public static void main(String[] args) {
        LOGGER.info("Setup resources (API)");
        final ResourceConfig config = new JerseyConfiguration();
        try {
            LOGGER.info("Setup http server");
            final Server server = new Server(PORT);
            ServletContextHandler context =
                    new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(config));
            context.addServlet(jerseyServlet, "/api/*");
            server.start();
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
            server.join();
        } catch (Exception e) {
            LOGGER.error("Error starting up the server", e);
        }
    }
}
