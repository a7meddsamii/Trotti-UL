package ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import java.time.LocalDate;

public abstract class StandardAccountCreationNode {

    protected StandardAccountCreationNode next;

    public StandardAccountCreationNode setNext(StandardAccountCreationNode next) {
        this.next = next;
        return this;
    }

    public abstract Account createStandardAccount(String name, LocalDate birthDate, Gender gender,
            Idul idul, Email email, Password password, Role role);

}
