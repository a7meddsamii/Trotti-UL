package ca.ulaval.glo4003.trotti.api.order.controllers;

import ca.ulaval.glo4003.trotti.api.order.dto.requests.PassListRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cart")
public interface CartResource {

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    Response getCart(@HeaderParam("Authorization") String tokenRequest);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response addToCart(@HeaderParam("Authorization") String tokenRequest,
                              @Valid PassListRequest passListRequest);


    @DELETE
    @Path("/{passId}")
    Response removeFromCart(@HeaderParam("Authorization") String tokenRequest,
                                   @PathParam("passId") String passId);


    @DELETE
    Response clearCart(@HeaderParam("Authorization") String tokenRequest);
}
