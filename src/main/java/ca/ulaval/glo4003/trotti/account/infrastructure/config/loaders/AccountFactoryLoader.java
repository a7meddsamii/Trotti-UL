package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.api.security.authentication.SecurityContextFactory;
import ca.ulaval.glo4003.trotti.account.api.security.authentication.jwtsecuritycontext.JwtSecurityContextFactoryAdapter;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountValidator;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.TechnicianCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.EmployeeCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StandardAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StudentCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.time.Clock;

public class AccountFactoryLoader extends Bootstrapper {

    @Override
    public void load() {
        this.loadFactory();
        this.loadSecurityContextFactory();
    }

    private void loadFactory() {

        AccountValidator accountValidator =
                new AccountValidator(this.resourceLocator.resolve(Clock.class));
        StandardAccountCreationNode standardChain = buildStandardAccountChain();
        AdminManagedAccountCreationNode adminManagedChain = buildAdminManagedAccountChain();
        AccountFactory accountFactory =
                new AccountFactory(accountValidator, standardChain, adminManagedChain);

        this.resourceLocator.register(AccountFactory.class, accountFactory);
    }

    private StandardAccountCreationNode buildStandardAccountChain() {
        StandardAccountCreationNode employee = new EmployeeCreationNode(this.resourceLocator.resolve(EmployeeRegistryProvider.class));
        StandardAccountCreationNode student = new StudentCreationNode();

        employee.setNext(student);

        return employee;
    }

    private AdminManagedAccountCreationNode buildAdminManagedAccountChain() {
        AdminManagedAccountCreationNode admin = new AdminCreationNode();
        AdminManagedAccountCreationNode technician = new TechnicianCreationNode();

        admin.setNext(technician);

        return admin;
    }

    private void loadSecurityContextFactory() {
        this.resourceLocator.register(SecurityContextFactory.class,
                new JwtSecurityContextFactoryAdapter());
    }
}
