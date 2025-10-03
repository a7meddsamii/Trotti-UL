package ca.ulaval.glo4003.trotti.api.account.resources;

import ca.ulaval.glo4003.trotti.api.account.dto.request.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.api.account.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.application.account.AccountApplicationService;
import ca.ulaval.glo4003.trotti.application.account.dto.AccountDto;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/api/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
    private static final String ACCOUNTS_ENDPOINT = "/api/accounts";
    private static final String PATH_SEPARATOR = "/";

    private final AccountApplicationService accountApplicationService;
    private final AccountApiMapper accountApiMapper;

    public AccountResource(
            AccountApplicationService accountApplicationService,
            AccountApiMapper accountApiMapper) {
        this.accountApplicationService = accountApplicationService;
        this.accountApiMapper = accountApiMapper;
    }

    @POST
    public Response createAccount(@Valid CreateAccountRequest request) {
        AccountDto accountDto = accountApiMapper.toAccountDto(request);

        accountApplicationService.createAccount(accountDto);

        URI location = URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + request.idul());
        return Response.created(location).build();
    }
}
