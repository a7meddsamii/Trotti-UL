package ca.ulaval.glo4003.trotti.account.infrastructure.provider;

import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.account.domain.values.Idul;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class EmployeeRegistryProviderTest {
    private static final String EMPLOYEES_IDUL_JSON = """
            	MALAU1
            	JUGIR9

            	LOPAY165
            	SODUP4
            """;

    private static final Idul EMPLOYEE_IDUL = Idul.from("LOPAY165");
    private static final Idul NON_EMPLOYEE_IDUL = Idul.from("NONEXIST");

    @TempDir
    private Path testingResourcePath;
    private Path temporaryFile;

    private EmployeeRegistryProvider employeeRegistryProvider;

    @BeforeEach
    void setup() {
        temporaryFile = testingResourcePath.resolve("employeeRegistry.json");
        employeeRegistryProvider = new JsonULavalEmployeeRegistryProvider(temporaryFile);
    }

    @Test
    void givenEmployeeIdul_whenCheckingExistence_thenReturnsTrue() throws Exception {
        Files.writeString(temporaryFile, EMPLOYEES_IDUL_JSON);

        boolean exists = employeeRegistryProvider.exist(EMPLOYEE_IDUL);

        Assertions.assertTrue(exists);
    }

    @Test
    void givenNonEmployeeIdul_whenCheckingExistence_thenReturnsFalse() throws Exception {
        Files.writeString(temporaryFile, EMPLOYEES_IDUL_JSON);

        boolean exists = employeeRegistryProvider.exist(NON_EMPLOYEE_IDUL);

        Assertions.assertFalse(exists);
    }
}
