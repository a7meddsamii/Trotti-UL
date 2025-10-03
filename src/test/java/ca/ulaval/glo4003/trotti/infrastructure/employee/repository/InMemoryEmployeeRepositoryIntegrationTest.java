package ca.ulaval.glo4003.trotti.infrastructure.employee.repository;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.employee.repository.EmployeeRepository;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryEmployeeRepositoryIntegrationTest {

    private static final Idul EMPLOYEE_IDUL = Idul.from("EMPLOYEE_IDUL");
    private static final Idul NON_EMPLOYEE_IDUL = Idul.from("NON_EMPLOYEE_IDUL");

    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        employeeRepository = new InMemoryEmployeeRepository(Set.of(EMPLOYEE_IDUL));
    }

    @Test
    void givenEmployeeIdul_whenSeeingIfEmployee_thenReturnTrue() {
        boolean result = employeeRepository.isEmployee(EMPLOYEE_IDUL);

        Assertions.assertTrue(result);
    }

    @Test
    void givenNonEmployeeIdul_whenSeeingIfEmployee_thenReturnFalse() {
        boolean result = employeeRepository.isEmployee(NON_EMPLOYEE_IDUL);

        Assertions.assertFalse(result);
    }
}
