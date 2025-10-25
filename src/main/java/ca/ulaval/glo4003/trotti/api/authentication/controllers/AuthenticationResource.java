package ca.ulaval.glo4003.trotti.api.authentication.controllers;

import ca.ulaval.glo4003.trotti.api.authentication.dto.LoginRequest;
import ca.ulaval.glo4003.trotti.api.authentication.dto.LoginResponse;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {
    private final AccountApplicationService accountApplicationService;

    public AuthenticationResource(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @POST
    @Path("/login")
    public LoginResponse login(@Valid LoginRequest request) {
        if (request == null)
            throw new InvalidParameterException("Please provide an email and a password to login.");

        Email email = Email.from(request.email());
        AuthenticationToken token = accountApplicationService.login(email, request.password());

        return new LoginResponse(token);
    }
}
