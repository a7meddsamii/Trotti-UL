package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.SessionTokenProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.SessionToken;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;

public class AccountApplicationService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final SessionTokenProvider sessionTokenProvider;

    public AccountApplicationService(
            AccountRepository accountRepository,
            AccountFactory accountFactory,
            SessionTokenProvider sessionTokenProvider) {
        this.accountRepository = accountRepository;
        this.accountFactory = accountFactory;
        this.sessionTokenProvider = sessionTokenProvider;
    }

    public Idul createAccount(AccountDto registration) {
        validateAccountDoesNotExist(registration.email(), registration.idul());
        Account account = accountFactory.create(registration.name(), registration.birthDate(),
                registration.gender(), registration.idul(), registration.email(),
                registration.password(), registration.role());
        accountRepository.save(account);

        return account.getIdul();
    }
    public Idul createAdminManagedAccount(AccountDto registration,
                                          AuthenticationToken creatorToken) {
        validateAccountDoesNotExist(registration.email(), registration.idul());
        Idul creatorIdul = authenticationService.authenticate(creatorToken);
        Account creatorAccount = accountRepository.findByIdul(creatorIdul)
                .orElseThrow(() -> new AuthenticationException("Invalid creator account"));
        Account account = accountFactory.create(registration.name(), registration.birthDate(),
                registration.gender(), registration.idul(), registration.email(),
                registration.password(), registration.role(), creatorAccount.getPermissions());
        accountRepository.save(account);
        
        return account.getIdul();
    }
	
    public SessionToken login(Email email, String rawPassword) {
        Account account = accountRepository.findByEmail(email)
                .filter(found -> found.verifyPassword(rawPassword))
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        return sessionTokenProvider.generateToken(account.getIdul(), account.getRole(),
                account.getPermissions());
    }

    private void validateAccountDoesNotExist(Email email, Idul idul) {
        if (accountRepository.findByEmail(email).isPresent()
                || accountRepository.findByIdul(idul).isPresent()) {
            throw new AlreadyExistsException("Email or Idul already in use");
        }
    }
}