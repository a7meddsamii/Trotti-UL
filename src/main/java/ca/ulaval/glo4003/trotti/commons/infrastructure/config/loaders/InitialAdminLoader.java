package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.time.LocalDate;
import java.util.Set;

public class InitialAdminLoader extends Bootstrapper {

    private static final String INITIAL_ADMIN_NAME = "INITIAL_ADMIN_NAME";
    private static final String INITIAL_ADMIN_EMAIL = "INITIAL_ADMIN_EMAIL";
    private static final String INITIAL_ADMIN_IDUL = "INITIAL_ADMIN_IDUL";
    private static final String INITIAL_ADMIN_BIRTHDATE = "INITIAL_ADMIN_BIRTHDATE";
    private static final String INITIAL_ADMIN_GENDER = "INITIAL_ADMIN_GENDER";
    private static final String INITIAL_ADMIN_PASSWORD = "INITIAL_ADMIN_PASSWORD";

    @Override
    public void load() {
        createInitialAdmin();
    }

    private void createInitialAdmin() {
        AccountRepository accountRepository = this.resourceLocator.resolve(AccountRepository.class);
        AccountFactory accountFactory = this.resourceLocator.resolve(AccountFactory.class);
        PasswordHasher passwordHasher = this.resourceLocator.resolve(PasswordHasher.class);

        String name = System.getenv(INITIAL_ADMIN_NAME);
        LocalDate birthDate = LocalDate.parse(System.getenv(INITIAL_ADMIN_BIRTHDATE));
        Gender gender = Gender.fromString(System.getenv(INITIAL_ADMIN_GENDER));
        Idul idul = Idul.from(System.getenv(INITIAL_ADMIN_IDUL));
        Email email = Email.from(System.getenv(INITIAL_ADMIN_EMAIL));
        Password password =
                Password.fromPlain(System.getenv(INITIAL_ADMIN_PASSWORD), passwordHasher);

        Set<Permission> initialAdminPermissions = Set.of(Permission.CREATE_EMPLOYEE,
                Permission.DELETE_EMPLOYEE, Permission.CREATE_ADMIN, Permission.DELETE_ADMIN);

        Account admin = accountFactory.create(name, birthDate, gender, idul, email, password,
                Role.ADMIN, initialAdminPermissions);

        accountRepository.save(admin);
    }
}
