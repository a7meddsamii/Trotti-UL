package ca.ulaval.glo4003.trotti.account.api.controllers;

import ca.ulaval.glo4003.trotti.account.api.dto.CreateAccountRequest;
import ca.ulaval.glo4003.trotti.account.api.mappers.AccountApiMapper;
import ca.ulaval.glo4003.trotti.account.application.AccountApplicationService;
import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
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

        accountApplicationService.createAccount(accountDto);

        URI location = URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + request.idul());
        return Response.created(location).build();
    }
    
    @Override
    public Response createAdminManagedAccount(String tokenHeader, CreateAccountRequest request) {
        AuthenticationToken token = AuthenticationToken.from(tokenHeader);
        AccountDto accountDto = accountApiMapper.toAccountDto(request);
        
        accountApplicationService.createAdminManagedAccount(accountDto, token);
        
        URI location = URI.create(ACCOUNTS_ENDPOINT + PATH_SEPARATOR + request.idul());
        return Response.created(location).build();
    }
}