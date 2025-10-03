package ca.ulaval.glo4003.trotti.infrastructure.employee;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EmployeeIdulRepository {
    
    private final Set<Idul> employees;
    
    public EmployeeIdulRepository(Collection<Idul> iduls) {
        this.employees = new HashSet<>(iduls);
    }

    public boolean exist(Idul idul) {
        return employees.contains(idul);
    }
}