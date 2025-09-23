package ca.ulaval.glo4003.trotti.application.mapper;

import ca.ulaval.glo4003.trotti.application.account.dto.CreateAccount;
import ca.ulaval.glo4003.trotti.domain.account.*;

public class AccountMapper {

    private final AccountFactory accountFactory;
    private final PasswordHasher passwordHasher;

    public AccountMapper(AccountFactory accountFactory, PasswordHasher passwordHasher) {
        this.accountFactory = accountFactory;
        this.passwordHasher = passwordHasher;
    }

    public Account create(CreateAccount request) {

        Email email = Email.from(request.email());
        Password password = new Password(request.password(), passwordHasher);
        Gender gender = Gender.fromString(request.gender());
        Idul idul = Idul.from(request.idul());

        return accountFactory.create(request.name(), request.birthDate(), gender, idul, email,
                password);
    }
}
