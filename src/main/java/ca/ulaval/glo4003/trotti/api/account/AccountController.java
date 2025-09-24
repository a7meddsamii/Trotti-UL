package ca.ulaval.glo4003.trotti.api.account;

import ca.ulaval.glo4003.trotti.api.account.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.application.account.AccountService;
import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/api/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {
    private static final String ACCOUNTS_ENDPOINT = "/api/accounts";

    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @POST
    public Response createAccount(@Valid CreateAccountRequest request) {
        CreateAccount createAccount = new CreateAccount(request.name(), request.birthDate(),
                request.gender(), request.idul(), request.email(), request.password());
        accountService.createAccount(createAccount);

        URI location = URI.create(ACCOUNTS_ENDPOINT + request.idul());
        return Response.created(location).build();
    }
}
