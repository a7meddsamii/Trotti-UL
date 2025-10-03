package ca.ulaval.glo4003.trotti.infrastructure.employee.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;

import ca.ulaval.glo4003.trotti.domain.employee.repository.EmployeeRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InMemoryEmployeeRepository implements EmployeeRepository {
    
    private final Set<Idul> employees;
    
    public InMemoryEmployeeRepository(Set<Idul> iduls) {
        this.employees = iduls;
    }

    @Override
    public boolean isEmployee(Idul idul) {
        return employees.contains(idul);
    }
}