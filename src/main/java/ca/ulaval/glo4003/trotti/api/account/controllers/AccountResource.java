package ca.ulaval.glo4003.trotti.api.account.controllers;

import ca.ulaval.glo4003.trotti.api.account.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
public interface AccountResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    Response createAccount(@Valid CreateAccountRequest request);
}
