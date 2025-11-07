package ca.ulaval.glo4003.trotti.commons.domain;

import java.util.Set;

import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeRegistryTest {

    private static final Idul EMPLOYEE_IDUL = Idul.from("EMPLOYEE_IDUL");
    private static final Idul NON_EMPLOYEE_IDUL = Idul.from("NON_EMPLOYEE_IDUL");

    private EmployeeRegistry employeeRepository;

    @BeforeEach
    void setup() {
        employeeRepository = new EmployeeRegistry(Set.of(EMPLOYEE_IDUL));
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
