package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.AccountRegistration;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Gender;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.Password;
import ca.ulaval.glo4003.trotti.domain.account.PasswordHasher;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.account.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.AlreadyExistsException;

public class AccountApplicationService {

    private final AuthenticationService authenticationService;
    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final PasswordHasher passwordHasher;

    public AccountApplicationService(
            AccountRepository accountRepository,
            AuthenticationService authenticationService,
            AccountFactory accountFactory,
            PasswordHasher passwordHasher) {
        this.accountRepository = accountRepository;
        this.authenticationService = authenticationService;
        this.accountFactory = accountFactory;
        this.passwordHasher = passwordHasher;
    }

    public Idul createAccount(AccountRegistration registration) {
        Email email = Email.from(registration.email());
        Idul idul = Idul.from(registration.idul());

        validateAccountDoesNotExist(email, idul);

        Account account = buildAccountFromRegistration(registration, email, idul);
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

    private Account buildAccountFromRegistration(AccountRegistration registration, Email email,
            Idul idul) {

        return accountFactory.create(registration.name(), registration.birthDate(),
                Gender.fromString(registration.gender()), idul, email,
                Password.createNew(registration.password(), passwordHasher));
    }

    private void validateAccountDoesNotExist(Email email, Idul idul) {
        if (accountRepository.findByEmail(email).isPresent()
                || accountRepository.findByIdul(idul).isPresent()) {
            throw new AlreadyExistsException(
                    "The email or idul used is already linked to an existing account");
        }
    }
}
