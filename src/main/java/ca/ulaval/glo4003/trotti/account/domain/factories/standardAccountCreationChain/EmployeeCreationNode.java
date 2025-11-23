package ca.ulaval.glo4003.trotti.account.domain.factories.standardAccountCreationChain;

import ca.ulaval.glo4003.trotti.account.domain.entities.Account;
import ca.ulaval.glo4003.trotti.account.domain.exceptions.AuthorizationException;
import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.*;
import ca.ulaval.glo4003.trotti.account.domain.values.Permission;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.time.LocalDate;
import java.util.Set;

public class EmployeeCreationNode extends StandardAccountCreationNode {

    private final Set<Permission> permissions =
            Set.of(Permission.MAKE_TRIP, Permission.REQUEST_MAINTENANCE);

    private final EmployeeRegistryProvider employeeRegistryProvider;

    public EmployeeCreationNode(EmployeeRegistryProvider employeeRegistryProvider) {
        this.employeeRegistryProvider = employeeRegistryProvider;
    }

    @Override
    protected Role responsibilityRole() {
        return Role.EMPLOYEE;
    }

    @Override
    protected Account createAccount(String name, LocalDate birthDate, Gender gender, Idul idul,
            Email email, Role role) {

        if (!employeeRegistryProvider.exists(idul)) {
            throw new AuthorizationException("Employee not found.");
        }

        return new Account(name, birthDate, gender, idul, email, role, permissions);
    }
}
