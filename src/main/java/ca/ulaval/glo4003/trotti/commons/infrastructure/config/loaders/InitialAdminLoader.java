package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.repositories.AccountRepository;
import ca.ulaval.glo4003.trotti.account.domain.services.PasswordHasher;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.AccountPermissions;
import ca.ulaval.glo4003.trotti.account.domain.values.permissions.Permission;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.EnvironmentReader;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.time.LocalDate;
import java.util.Set;

public class InitialAdminLoader extends Bootstrapper {

    @Override
    public void load() {
        createInitialAdmin();
    }

    private void createInitialAdmin() {
        try {
            AccountRepository accountRepository =
                    this.resourceLocator.resolve(AccountRepository.class);
            AccountFactory accountFactory = this.resourceLocator.resolve(AccountFactory.class);
            PasswordHasher passwordHasher = this.resourceLocator.resolve(PasswordHasher.class);

            EnvironmentReader env = EnvironmentReader.getInstance();

            String name = env.get("ADMIN_NAME");
            LocalDate birthDate = LocalDate.parse(env.get("ADMIN_BIRTHDATE"));
            Gender gender = Gender.fromString(env.get("ADMIN_GENDER"));
            Idul idul = Idul.from(env.get("ADMIN_IDUL"));
            Email email = Email.from(env.get("ADMIN_EMAIL"));
            Password password = Password.fromPlain(env.get("ADMIN_PASSWORD"), passwordHasher);

            Set<Permission> initialAdminPermissions = Set.of(AccountPermissions.CREATE_TECHNICIAN,
                    AccountPermissions.DELETE_TECHNICIAN);

            Account admin = accountFactory.createInitialAdmin(name, birthDate, gender, idul, email,
                    password, Role.ADMIN, initialAdminPermissions);

            accountRepository.save(admin);
        } catch (Exception exception) {
        }
    }
}
