package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.factories.AccountValidator;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.AdminCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.AdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.NoAdminManagedAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.TechnicianCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.EmployeeCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.NoStandardAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.StudentCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.StandardAccountCreationNode;
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
        StandardAccountCreationNode userChain =
                new EmployeeCreationNode(new StudentCreationNode(new NoStandardAccountCreationNode()));
        AdminManagedAccountCreationNode companyChain = new AdminCreationNode(
                new TechnicianCreationNode(new NoAdminManagedAccountCreationNode()));
        AccountFactory accountFactory =
                new AccountFactory(accountValidator, userChain, companyChain);

        this.resourceLocator.register(AccountFactory.class, accountFactory);
    }
}
