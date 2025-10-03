package ca.ulaval.glo4003.trotti.infrastructure.employee;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeIdulCsvReaderTest {

    private static final String EMPTY_CSV_PATH = "data/empty.csv";
    private static final String INVALID_CSV_PATH = "invalid_path.csv";
    private static final String VALID_CSV_PATH = "data/valid.csv";

    private EmployeeIdulCsvReader employeeIdulCsvReader;

    @BeforeEach
    void setup() {
        employeeIdulCsvReader = new EmployeeIdulCsvReader();
    }

    @Test
    void givenEmptyCsv_whenReading_thenNoIdulsReturned() {
        Set<Idul> iduls = employeeIdulCsvReader.readFromClasspath(EMPTY_CSV_PATH);

        Assertions.assertTrue(iduls.isEmpty());
    }

    @Test
    void givenValidCsv_whenReading_thenIdulsReturned() {
        Set<Idul> iduls = employeeIdulCsvReader.readFromClasspath(VALID_CSV_PATH);

        Assertions.assertEquals(2, iduls.size());
    }

    @Test
    void givenInvalidPath_whenReading_thenExceptionThrown() {
        Assertions.assertThrows(RuntimeException.class, () -> employeeIdulCsvReader.readFromClasspath(INVALID_CSV_PATH));
    }

}