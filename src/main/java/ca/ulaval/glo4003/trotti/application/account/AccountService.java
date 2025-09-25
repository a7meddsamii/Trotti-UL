package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.account.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.shared.exception.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class AccountService {

    private final AccountMapper mapper;
    private final AuthenticationService authService;
    private final AccountRepository repository;

    public AccountService(
            AccountRepository repository,
            AccountMapper mapper,
            AuthenticationService authService) {
        this.repository = repository;
        this.mapper = mapper;
        this.authService = authService;
    }

    public Idul createAccount(CreateAccount request) {
        Email email = Email.from(request.email());
        Idul idul = Idul.from(request.idul());
        validateAccountDoesNotExist(email, idul);

        Account account = mapper.create(request);
        repository.save(account);
        return account.getIdul();
    }

    public AuthenticationToken login(String emailInput, String rawPassword) {
        Email email = Email.from(emailInput);
        Account account = repository.findByEmail(email)
                .orElseThrow(() -> new InvalidParameterException("Invalid email or password"));

        if (!account.getPassword().matches(rawPassword)) {
            throw new InvalidParameterException("Invalid email or password");
        }
        return authService.generateToken(account.getIdul());
    }

    private void validateAccountDoesNotExist(Email email, Idul idul) {
        if (repository.findByEmail(email).isPresent()) {
            throw new AlreadyExistsException("The email " + email + " is already used");
        }
        if (repository.findByIdul(idul).isPresent()) {
            throw new AlreadyExistsException("The idul " + idul + " is already used");
        }
    }
}
