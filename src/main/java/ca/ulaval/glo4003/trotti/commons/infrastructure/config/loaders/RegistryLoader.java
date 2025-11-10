package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.EmployeeRegistryGateway;
import ca.ulaval.glo4003.trotti.commons.domain.SchoolSessionGateway;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.sessions.SessionMapper;
import ca.ulaval.glo4003.trotti.commons.infrastructure.services.JsonSessionGateway;
import ca.ulaval.glo4003.trotti.commons.infrastructure.services.JsonULavalEmployeeRegistryGateway;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.config.json.CustomJsonProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;

public class RegistryLoader extends Bootstrapper {
    private static final Path EMPLOYEE_IDUL_CSV_PATH = Path.of("/app/data/Employe.e.s.csv");
    private static final Path SEMESTER_DATA_FILE_PATH = Path.of("/app/data/semesters-252627.json");

    @Override
    public void load() {
        loadSessionProvider();
        loadEmployeeIdulRegistry();
    }

    private void loadSessionProvider() {
        // TODO not done
        ObjectMapper objectMapper = CustomJsonProvider.getMapper();
        SessionMapper sessionMapper = this.resourceLocator.resolve(SessionMapper.class);
        SchoolSessionGateway schoolSessionGateway =
                new JsonSessionGateway(SEMESTER_DATA_FILE_PATH, sessionMapper, objectMapper);
        this.resourceLocator.register(SchoolSessionGateway.class, schoolSessionGateway);
    }

    private void loadEmployeeIdulRegistry() {
        // TODO not done
        EmployeeRegistryGateway employeeRegistryGateway =
                new JsonULavalEmployeeRegistryGateway(EMPLOYEE_IDUL_CSV_PATH);

        this.resourceLocator.register(EmployeeRegistryGateway.class, employeeRegistryGateway);
    }
}
