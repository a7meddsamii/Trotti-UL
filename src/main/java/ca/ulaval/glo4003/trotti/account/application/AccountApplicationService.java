package ca.ulaval.glo4003.trotti.account.application;

import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.LoginDto;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
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
    private final AuthenticationProvider authenticationProvider;

    public AccountApplicationService(
        AccountRepository accountRepository,
        AccountFactory accountFactory,
        SessionTokenProvider sessionTokenProvider, AuthenticationProvider authenticationProvider) {
        this.accountRepository = accountRepository;
        this.accountFactory = accountFactory;
        this.sessionTokenProvider = sessionTokenProvider;
        this.authenticationProvider = authenticationProvider;
    }

    public Idul createAccount(RegistrationDto registrationDto) {
        AccountDto accountDto = authenticationProvider.register(registrationDto);
        validateAccountDoesNotExist(accountDto.email(), accountDto.idul());
        Account account = accountFactory.create(accountDto.name(), accountDto.birthDate(),
            accountDto.gender(), accountDto.idul(), accountDto.email(),
            accountDto.role());
        accountRepository.save(account);

        return account.getIdul();
    }

    public Idul createAdminManagedAccount(RegistrationDto registrationDto, Idul creatorIdul) {
        AccountDto accountDto = authenticationProvider.register(registrationDto);
        validateAccountDoesNotExist(accountDto.email(), accountDto.idul());

        Account creatorAccount = accountRepository.findByIdul(creatorIdul)
                .orElseThrow(() -> new AuthenticationException("Invalid creator account"));
        Account account = accountFactory.create(accountDto.name(), accountDto.birthDate(),
            accountDto.gender(), accountDto.idul(), accountDto.email(),
            accountDto.role(), creatorAccount.getPermissions());
        accountRepository.save(account);

        return account.getIdul();
    }

    public SessionToken login(Email email, String rawPassword) {
        validateCredentials(email, rawPassword);

        Account account = accountRepository.findByEmail(email)
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
    
    private void validateCredentials(Email email, String rawPassword){
        LoginDto passwordLoginDto = new LoginDto(email, rawPassword);
        if (!authenticationProvider.verify(passwordLoginDto)) {
            throw new AuthenticationException("Invalid email or password");
        }
    }
}
