package ca.ulaval.glo4003.trotti.api.authentication.controllers;

import ca.ulaval.glo4003.trotti.api.authentication.dto.LoginRequest;
import ca.ulaval.glo4003.trotti.api.authentication.dto.LoginResponse;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.authentication.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import jakarta.ws.rs.core.Response;

public class AuthenticationController implements AuthenticationResource {
    private final AccountApplicationService accountApplicationService;

    public AuthenticationController(AccountApplicationService accountApplicationService) {
        this.accountApplicationService = accountApplicationService;
    }

    @Override
    public Response login(LoginRequest request) {
        if (request == null)
            throw new InvalidParameterException("Please provide an email and a password to login.");

        Email email = Email.from(request.email());
        AuthenticationToken token = accountApplicationService.login(email, request.password());

        return Response.ok().entity(new LoginResponse(token)).build();
    }
}
