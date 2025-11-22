package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.application.AuthenticationProvider;
import ca.ulaval.glo4003.trotti.account.application.dto.AccountDto;
import ca.ulaval.glo4003.trotti.account.application.dto.RegistrationDto;
import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.time.LocalDate;
import java.util.Set;

public class InitialAdminLoader extends Bootstrapper {

    private static final String INITIAL_ADMIN_NAME = "ADMIN_NAME";
    private static final String INITIAL_ADMIN_EMAIL = "ADMIN_EMAIL";
    private static final String INITIAL_ADMIN_IDUL = "ADMIN_IDUL";
    private static final String INITIAL_ADMIN_BIRTHDATE = "ADMIN_BIRTHDATE";
    private static final String INITIAL_ADMIN_GENDER = "ADMIN_GENDER";
    private static final String INITIAL_ADMIN_PASSWORD = "ADMIN_PASSWORD";

    @Override
    public void load() {
        createInitialAdmin();
    }

    private void createInitialAdmin() {
        AccountRepository accountRepository = this.resourceLocator.resolve(AccountRepository.class);
        AccountFactory accountFactory = this.resourceLocator.resolve(AccountFactory.class);
        AuthenticationProvider authenticationProvider =
                this.resourceLocator.resolve(AuthenticationProvider.class);

        String name = System.getenv(INITIAL_ADMIN_NAME);
        LocalDate birthDate = LocalDate.parse(System.getenv(INITIAL_ADMIN_BIRTHDATE));
        Gender gender = Gender.fromString(System.getenv(INITIAL_ADMIN_GENDER));
        Idul idul = Idul.from(System.getenv(INITIAL_ADMIN_IDUL));
        Email email = Email.from(System.getenv(INITIAL_ADMIN_EMAIL));
        String password = System.getenv(INITIAL_ADMIN_PASSWORD);

        RegistrationDto registrationDto =
                new RegistrationDto(name, birthDate, gender, idul, email, password, Role.ADMIN);

        AccountDto accountDto = authenticationProvider.register(registrationDto);
        Set<Permission> permissions = Set.of(Permission.DELETE_EMPLOYEE, Permission.CREATE_EMPLOYEE,
                Permission.CREATE_ADMIN, Permission.DELETE_ADMIN);

        Account admin = accountFactory.create(accountDto.name(), accountDto.birthDate(),
                accountDto.gender(), accountDto.idul(), accountDto.email(), accountDto.role(),
                permissions);

        accountRepository.save(admin);

    }
}
