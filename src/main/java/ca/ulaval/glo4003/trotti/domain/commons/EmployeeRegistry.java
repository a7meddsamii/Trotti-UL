package ca.ulaval.glo4003.trotti.domain.commons;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.util.Set;

public class EmployeeRegistry {

    private final Set<Idul> employees;

    public EmployeeRegistry(Set<Idul> iduls) {
        this.employees = iduls;
    }

    public boolean isEmployee(Idul idul) {
        return employees.contains(idul);
    }
}
