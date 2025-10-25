package ca.ulaval.glo4003.trotti.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.domain.account.values.Idul;
import ca.ulaval.glo4003.trotti.domain.commons.EmployeeRegistry;
import ca.ulaval.glo4003.trotti.domain.commons.SessionRegistry;
import ca.ulaval.glo4003.trotti.infrastructure.commons.sessions.mappers.SessionMapper;
import ca.ulaval.glo4003.trotti.infrastructure.commons.stations.mappers.StationMapper;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.EmployeeIdulCsvProvider;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.SessionProvider;
import ca.ulaval.glo4003.trotti.infrastructure.config.providers.StationProvider;
import java.nio.file.Path;
import java.util.Set;

public class RegistryLoader extends Bootstrapper {
    private static final Path EMPLOYEE_IDUL_CSV_PATH = Path.of("/app/data/Employe.e.s.csv");
    private static final Path SEMESTER_DATA_FILE_PATH = Path.of("/app/data/semesters-252627.json");
    private static final Path STATION_DATA_FILE_PATH =
            Path.of("/app/data/campus-delivery-location.json");

    @Override
    public void load() {
        loadSessionProvider();
        loadEmployeeIdulRegistry();
        loadStationProvider();
    }

    private void loadSessionProvider() {
        SessionMapper sessionMapper = new SessionMapper();
        SessionProvider.initialize(SEMESTER_DATA_FILE_PATH, sessionMapper);
        SessionRegistry sessionRegistry =
                new SessionRegistry(SessionProvider.getInstance().getSessions());

        this.resourceLocator.register(SessionProvider.class, SessionProvider.getInstance());
        this.resourceLocator.register(SessionRegistry.class, sessionRegistry);
    }

    private void loadEmployeeIdulRegistry() {
        EmployeeIdulCsvProvider reader = new EmployeeIdulCsvProvider();
        Set<Idul> employeesIduls = reader.readFromClasspath(EMPLOYEE_IDUL_CSV_PATH);
        EmployeeRegistry employeeRegistry = new EmployeeRegistry(employeesIduls);
        this.resourceLocator.register(EmployeeRegistry.class, employeeRegistry);
    }

    private void loadStationProvider() {
        StationMapper stationMapper = new StationMapper();
        StationProvider.initialize(STATION_DATA_FILE_PATH, stationMapper);
        this.resourceLocator.register(StationProvider.class, StationProvider.getInstance());
    }
}
