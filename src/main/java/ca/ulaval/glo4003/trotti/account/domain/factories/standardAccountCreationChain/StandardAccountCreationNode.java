package ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.UnableToCreateAccountException;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import java.time.LocalDate;

public abstract class StandardAccountCreationNode {

    protected StandardAccountCreationNode next;

    public StandardAccountCreationNode setNext(StandardAccountCreationNode next) {
        this.next = next;
        return this;
    }

    public Account createStandardAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Password password, Role role) {
        if (responsibilityRole().equals(role)) {
            return createAccount(name, birthDate, gender, idul, email, password, role);
        } else if (next != null) {
            return next.createStandardAccount(name, birthDate, gender, idul, email, password, role);
        } else {
            throw new UnableToCreateAccountException("unable to create account");
        }
    }

    protected abstract Account createAccount(String name, LocalDate birthDate, Gender gender,
            Idul idul, Email email, Password password, Role role);

    protected abstract Role responsibilityRole();
}
