package ca.ulaval.glo4003.trotti.commons.domain;

import ca.ulaval.glo4003.trotti.commons.domain.values.ids.Idul;

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
