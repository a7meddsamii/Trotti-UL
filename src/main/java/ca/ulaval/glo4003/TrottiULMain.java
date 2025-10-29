package ca.ulaval.glo4003;

import ca.ulaval.glo4003.trotti.infrastructure.config.JerseyConfiguration;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            context.addServlet(new ServletHolder(new HttpServlet() {
                @Override
                protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                        throws IOException {
                    resp.setContentType("text/html");
                    try (var input =
                            getClass().getClassLoader().getResourceAsStream("swagger-ui.html")) {
                        if (input != null) {
                            input.transferTo(resp.getOutputStream());
                        } else {
                            resp.sendError(404);
                        }
                    }
                }
            }), "/swagger-ui");

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

            server.join();
        } catch (Exception e) {
            LOGGER.error("Error starting up the server", e);
        }
    }
}
