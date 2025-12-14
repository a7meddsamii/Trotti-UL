package ca.ulaval.glo4003.trotti.heartbeat.api.controllers;

import ca.ulaval.glo4003.trotti.JerseyTestApi;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeartbeatControllerTest {

    private JerseyTestApi api;

    @BeforeEach
    public void setup() {
        api = new JerseyTestApi(new ResourceConfig(HeartbeatController.class));
        api.start();
    }

    @AfterEach
    public void tearDown() {
        api.stop();
    }

    @Test
    public void whenHeartbeat_thenReturns200() {
        Response response = api.path("/heartbeat").request().get();

        Assertions.assertEquals(200, response.getStatus());
    }

}
