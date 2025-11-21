package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import jakarta.ws.rs.core.Response;
import java.net.URI;

public class AccountController implements AccountResource {
    private static final String ACCOUNTS_ENDPOINT = "/api/accounts";
    private static final String PATH_SEPARATOR = "/";

    private final AccountApplicationService accountApplicationService;
    private final AccountApiMapper accountApiMapper;

    public AccountController(
            AccountApplicationService accountApplicationService,
            AccountApiMapper accountApiMapper) {
        this.accountApplicationService = accountApplicationService;
        this.accountApiMapper = accountApiMapper;
    }

    @Override
    public Response createAccount(CreateAccountRequest request) {
        AccountDto accountDto = accountApiMapper.toAccountDto(request);

        Idul idul = accountApplicationService.createAccount(accountDto);

        URI location = URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + idul);
        return Response.created(location).build();
    }

    @Override
    public Response createAdminManagedAccount(Idul userId, CreateAccountRequest request) {
        AccountDto accountDto = accountApiMapper.toAccountDto(request);
		
		Idul idul = accountApplicationService.createAdminManagedAccount(accountDto, userId);

        URI location = URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + idul);
        return Response.created(location).build();
    }
}
