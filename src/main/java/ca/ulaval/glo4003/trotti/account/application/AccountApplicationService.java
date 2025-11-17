package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.IdentityAccountDto;
import ca.ulaval.glo4003.trotti.account.application.port.AccountService;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AlreadyExistsException;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthenticationException;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.AuthenticationService;
import ca.ulaval.glo4003.trotti.account.domain.values.AuthenticationToken;
import ca.ulaval.glo4003.trotti.account.domain.values.Email;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.exceptions.NotFoundException;

import java.util.Set;

public class AccountApplicationService implements AccountService {

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
	
	@Override
	public IdentityAccountDto findByEmail(ca.ulaval.glo4003.trotti.commons.domain.Email email) {
		Account account = accountRepository.findByEmail( Email.from(email.toString()))
				.orElseThrow(() -> new NotFoundException("Account not found"));
		
		return new IdentityAccountDto(
				account.getIdul(),
				"TECHNICIAN",
				Set.of("START_MAINTENANCE", "END_MAINTENANCE", "MAKE_TRIP"),
				account.getPassword().toString(),
				account.getPassword().getHasher()
		);
	}
}
