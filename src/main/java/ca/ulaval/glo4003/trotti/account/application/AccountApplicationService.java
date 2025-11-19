package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;

public class AccountApplicationService {

    private final AuthenticationService authenticationService;
    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;

    public AccountApplicationService(
            AccountRepository accountRepository,
            AuthenticationService authenticationService,
            AccountFactory accountFactory) {
        this.accountRepository = accountRepository;
        this.authenticationService = authenticationService;
        this.accountFactory = accountFactory;
    }

    public Idul createUserAccount(AccountDto registration) {
        validateAccountDoesNotExist(registration.email(), registration.idul());
        Account account = accountFactory.create(registration.name(), registration.birthDate(),
                registration.gender(), registration.idul(), registration.email(),
                registration.password(), registration.role());
        accountRepository.save(account);

        return account.getIdul();
    }

    public AuthenticationToken login(Email email, String rawPassword) {
        Account account = accountRepository.findByEmail(email)
                .filter(found -> found.verifyPassword(rawPassword))
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        return authenticationService.generateToken(account.getIdul());
    }

    private void validateAccountDoesNotExist(Email email, Idul idul) {
        if (accountRepository.findByEmail(email).isPresent()
                || accountRepository.findByIdul(idul).isPresent()) {
            throw new AlreadyExistsException("Email or Idul already in use");
        }
    }
}
