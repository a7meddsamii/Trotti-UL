package ca.ulaval.glo4003.trotti.domain.employee.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;

public interface EmployeeRepository {
    boolean isEmployee(Idul idul);
}
