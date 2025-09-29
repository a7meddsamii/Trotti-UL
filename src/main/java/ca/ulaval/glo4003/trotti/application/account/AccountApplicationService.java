package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.AccountDto;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.account.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.commons.Email;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.AlreadyExistsException;

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

    public Idul createAccount(AccountDto registration) {
        validateAccountDoesNotExist(registration.email(), registration.idul());
        Account account = accountFactory.create(registration.name(), registration.birthDate(),
                registration.gender(), registration.idul(), registration.email(),
                registration.password());
        accountRepository.save(account);

        return account.getIdul();
    }

    public AuthenticationToken login(String emailInput, String rawPassword) {
        Email email = Email.from(emailInput);
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));
        account.verifyPassword(rawPassword);

        return authenticationService.generateToken(account.getIdul());
    }

    private void validateAccountDoesNotExist(Email email, Idul idul) {
        if (accountRepository.findByEmail(email).isPresent()
                || accountRepository.findByIdul(idul).isPresent()) {
            throw new AlreadyExistsException(
                    "The email or idul used is already linked to an existing account");
        }
    }
}
