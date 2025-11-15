package ca.ulaval.glo4003.trotti.account.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.account.domain.factories.AccountFactory;
import ca.ulaval.glo4003.trotti.account.domain.factories.BirthdayValidation;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.AdminCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.CompanyAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.NoCompanyAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.companyAccountCreationChain.TechnicianCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.EmployeeCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.NoUserAccountCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.StudentCreationNode;
import ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain.UserAccountCreationNode;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.time.Clock;

public class AccountFactoryLoader extends Bootstrapper {

    @Override
    public void load() {
        this.loadFactory();
    }

    private void loadFactory() {

        BirthdayValidation birthdayValidation =
                new BirthdayValidation(this.resourceLocator.resolve(Clock.class));
        UserAccountCreationNode userChain =
                new EmployeeCreationNode(new StudentCreationNode(new NoUserAccountCreationNode()));
        CompanyAccountCreationNode companyChain = new AdminCreationNode(
                new TechnicianCreationNode(new NoCompanyAccountCreationNode()));
        AccountFactory accountFactory =
                new AccountFactory(birthdayValidation, userChain, companyChain);

        this.resourceLocator.register(AccountFactory.class, accountFactory);
    }
}
