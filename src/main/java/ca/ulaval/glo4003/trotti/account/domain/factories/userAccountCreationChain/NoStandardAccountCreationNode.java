package ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.UnableToCreateAccountException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import java.time.LocalDate;

public class NoStandardAccountCreationNode extends StandardAccountCreationNode {

    @Override
    public Account createStandardAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password, Role role) {
        throw new UnableToCreateAccountException("unable to create account");
    }

}
