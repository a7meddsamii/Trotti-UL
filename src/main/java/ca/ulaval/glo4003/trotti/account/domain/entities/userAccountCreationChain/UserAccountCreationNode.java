package ca.ulaval.glo4003.trotti.account.domain.entities.userAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;

import java.time.LocalDate;

public abstract class UserAccountCreationNode {

    protected UserAccountCreationNode next;

    public abstract Account CreateUserAccount(String name,
                                              LocalDate birthDate,
                                              Gender gender,
                                              Idul idul,
                                              Email email,
                                              Password password,
                                              Role role);


}
