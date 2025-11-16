package ca.ulaval.glo4003.trotti.account.domain.factories.userAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import java.time.LocalDate;

public abstract class StandardAccountCreationNode {

    protected StandardAccountCreationNode next;

    public abstract Account createStandardAccount(String name, LocalDate birthDate, Gender gender,
                                                  Idul idul, Email email, Password password, Role role);

}
