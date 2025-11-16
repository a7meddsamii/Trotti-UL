package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountValidator;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.AdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.NoAdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.adminManagedAccountCreationChain.TechnicianCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.EmployeeCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.NoStandardAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StandardAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain.StudentCreationNode;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.time.Clock;

public class AccountFactoryLoader extends Bootstrapper {

    @Override
    public void load() {
        this.loadFactory();
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
        var employee = new EmployeeCreationNode();
        var student = new StudentCreationNode();
        var terminator = new NoStandardAccountCreationNode();

        employee.setNext(student);
        student.setNext(terminator);

        return employee;
    }

    private AdminManagedAccountCreationNode buildAdminManagedAccountChain() {
        var admin = new AdminCreationNode();
        var technician = new TechnicianCreationNode();
        var terminator = new NoAdminManagedAccountCreationNode();

        admin.setNext(technician);
        technician.setNext(terminator);

        return admin;
    }
}
