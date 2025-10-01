package ca.ulaval.glo4003.trotti.application.account;

import ca.ulaval.glo4003.trotti.application.account.dto.AccountDto;
import ca.ulaval.glo4003.trotti.domain.account.Account;
import ca.ulaval.glo4003.trotti.domain.account.AccountFactory;
import ca.ulaval.glo4003.trotti.domain.account.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.domain.account.repository.AccountRepository;
import ca.ulaval.glo4003.trotti.domain.account.values.Email;
import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationService;
import ca.ulaval.glo4003.trotti.domain.authentication.AuthenticationToken;
import ca.ulaval.glo4003.trotti.domain.commons.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.domain.order.BuyerFactory;
import ca.ulaval.glo4003.trotti.domain.order.repository.BuyerRepository;

public class AccountApplicationService {

    private final AuthenticationService authenticationService;
    private final AccountRepository accountRepository;
    private final BuyerRepository buyerRepository;
    // eventually TravelerRepo??
    private final AccountFactory accountFactory;
    private final BuyerFactory buyerFactory;

    public AccountApplicationService(
            AccountRepository accountRepository,
            BuyerRepository buyerRepository,
            AuthenticationService authenticationService,
            AccountFactory accountFactory,
            BuyerFactory buyerFactory) {
        this.accountRepository = accountRepository;
        this.buyerRepository = buyerRepository;
        this.authenticationService = authenticationService;
        this.accountFactory = accountFactory;
        this.buyerFactory = buyerFactory;
    }

    public Idul createAccount(AccountDto registration) {
        validateAccountDoesNotExist(registration.email(), registration.idul());
        Account account = accountFactory.create(registration.name(), registration.birthDate(),
                registration.gender(), registration.idul(), registration.email(),
                registration.password());
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
