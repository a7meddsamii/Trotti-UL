package ca.ulaval.glo4003.trotti.account.infrastructure.provider;

import ca.ulaval.glo4003.trotti.account.domain.provider.EmployeeRegistryProvider;
import ca.ulaval.glo4003.trotti.commons.domain.Idul;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JsonEmployeeRegistryProviderTest {
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
        employeeRegistryProvider = new JsonEmployeeRegistryProvider(temporaryFile);
    }

    @Test
    void givenEmployeeIdul_whenCheckingExistence_thenReturnsTrue() throws IOException {
        Files.writeString(temporaryFile, EMPLOYEES_IDUL_JSON);

        boolean exists = employeeRegistryProvider.exists(EMPLOYEE_IDUL);

        Assertions.assertTrue(exists);
    }

    @Test
    void givenNonEmployeeIdul_whenCheckingExistence_thenReturnsFalse() throws IOException {
        Files.writeString(temporaryFile, EMPLOYEES_IDUL_JSON);

        boolean exists = employeeRegistryProvider.exists(NON_EMPLOYEE_IDUL);

        Assertions.assertFalse(exists);
    }
}
