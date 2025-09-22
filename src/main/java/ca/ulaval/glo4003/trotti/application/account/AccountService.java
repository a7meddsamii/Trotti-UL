package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.application.mapper.AccountMapper;
import ca.ulaval.glo4003.trotti.application.port.TokenPort;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.Email;
import ca.ulaval.glo4003.trotti.domain.account.Idul;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.shared.exception.InvalidParameterException;

public class AccountService {

    private final AccountMapper mapper;
    private final TokenPort token;
    private final AccountRepository repository;

    public AccountService(AccountRepository repository, AccountMapper mapper, TokenPort token) {
        this.repository = repository;
        this.mapper = mapper;
        this.token = token;
    }

    public void createAccount(CreateAccount request) {
        Email email = Email.from(request.email());
        Idul idul = Idul.from(request.idul());

        Account account = mapper.create(request);
        repository.save(account);
    }

    public String login(String emailInput, String rawPassword) {
        Email email = Email.from(emailInput);
        Account account = repository.findByEmail(email);

        if (!account.getPassword().matches(rawPassword)) {
            throw new InvalidParameterException("");
        }

        return token.generateToken(account.getIdul());
    }
}
