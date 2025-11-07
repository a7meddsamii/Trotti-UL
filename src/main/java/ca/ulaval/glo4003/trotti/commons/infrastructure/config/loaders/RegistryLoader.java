package ca.ulaval.glo4003.trotti.commons.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.commons.domain.values.Idul;
import ca.ulaval.glo4003.trotti.commons.domain.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.commons.domain.values.SessionEnum;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.employees.EmployeeIdulCsvProvider;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.sessions.SessionMapper;
import ca.ulaval.glo4003.trotti.commons.infrastructure.config.providers.sessions.SessionProvider;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import java.nio.file.Path;
import java.util.Set;

public class RegistryLoader extends Bootstrapper {
    private static final Path EMPLOYEE_IDUL_CSV_PATH = Path.of("/app/data/Employe.e.s.csv");
    private static final Path SEMESTER_DATA_FILE_PATH = Path.of("/app/data/semesters-252627.json");

    @Override
    public void load() {
        loadSessionProvider();
        loadEmployeeIdulRegistry();
    }

    private void loadSessionProvider() {
        SessionMapper sessionMapper = new SessionMapper();
        SessionProvider.initialize(SEMESTER_DATA_FILE_PATH, sessionMapper);
        SessionEnum sessionEnum = new SessionEnum(SessionProvider.getInstance().getSessions());

        this.resourceLocator.register(SessionProvider.class, SessionProvider.getInstance());
        this.resourceLocator.register(SessionEnum.class, sessionEnum);
    }

    private void loadEmployeeIdulRegistry() {
        EmployeeIdulCsvProvider reader = new EmployeeIdulCsvProvider();
        Set<Idul> employeesIduls = reader.readFromClasspath(EMPLOYEE_IDUL_CSV_PATH);
        EmployeeRegistry employeeRegistry = new EmployeeRegistry(employeesIduls);
        this.resourceLocator.register(EmployeeRegistry.class, employeeRegistry);
    }
}
