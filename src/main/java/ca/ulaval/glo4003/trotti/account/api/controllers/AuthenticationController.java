package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.LoginRequest;
import ca.ulaval.glo4003.trotti.account.api.dto.LoginResponse;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.commons.exceptions.InvalidParameterException;
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
