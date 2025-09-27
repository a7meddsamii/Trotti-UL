package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.AccountRegistration;
import ca.ulaval.glo4003.trotti.domain.account.*;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.account.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;

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

        Account account = createDomainAccount(registration, email, idul);
        accountRepository.save(account);

        return account.getIdul();
    }

    public AuthenticationToken login(String emailInput, String rawPassword) {
        Email email = Email.from(emailInput);

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));
        account.authenticate(rawPassword);

        return authenticationService.generateToken(account.getIdul());
    }

    private Account createDomainAccount(AccountRegistration registration, Email email, Idul idul) {

        return accountFactory.create(registration.name(), parseBirthDate(registration.birthDate()),
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

    private LocalDate parseBirthDate(String birthDate) {
        try {
            return LocalDate.parse(birthDate);
        } catch (DateTimeException exception) {
            throw new InvalidParameterException("Invalid date format. Expected yyyy-MM-dd.");
        }
    }
}
