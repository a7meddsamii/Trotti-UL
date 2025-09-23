package ca.ulaval.glo4003.trotti.api.account;

import ca.ulaval.glo4003.trotti.api.account.dto.request.LoginRequest;
import ca.ulaval.glo4003.trotti.api.account.dto.response.LoginResponse;
import ca.ulaval.glo4003.trotti.application.account.AccountService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
    private final AccountService service;

    public AuthController(AccountService service) {
        this.service = service;
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        AuthenticationToken token = service.login(request.email(), request.password());
        LoginResponse response = new LoginResponse(token);

        return Response.ok(response).build();
    }

}
