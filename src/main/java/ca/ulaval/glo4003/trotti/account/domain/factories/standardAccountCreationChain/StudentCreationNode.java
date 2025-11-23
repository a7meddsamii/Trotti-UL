package ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;

public class StudentCreationNode extends StandardAccountCreationNode {

    private final Set<Permission> permissions = Set.of(Permission.CART_MODIFICATION,
            Permission.ORDER_CONFIRM, Permission.MAKE_TRIP, Permission.REQUEST_MAINTENANCE);

    @Override
    protected Role responsibilityRole() {
        return Role.STUDENT;
    }

    @Override
    protected Account createAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Role role) {
        return new Account(name, birthDate, gender, idul, email, role, permissions, Set.of());
    }
}
