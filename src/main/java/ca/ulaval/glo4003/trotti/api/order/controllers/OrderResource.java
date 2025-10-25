package ca.ulaval.glo4003.trotti.api.order.controllers;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PaymentInfoRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cart/confirm")
public interface OrderResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response confirm(@HeaderParam("Authorization") String tokenRequest,
                            PaymentInfoRequest paymentInfoRequest);
}
