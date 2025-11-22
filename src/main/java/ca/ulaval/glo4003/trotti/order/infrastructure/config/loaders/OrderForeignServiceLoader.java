package ca.ulaval.glo4003.trotti.order.infrastructure.config.loaders;

import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.config.json.CustomJsonProvider;
import ca.ulaval.glo4003.trotti.order.domain.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.order.infrastructure.provider.sessions.JsonSchoolSessionProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;

public class OrderForeignServiceLoader extends Bootstrapper {
    private static final Path SEMESTER_DATA_FILE_PATH = Path.of("/app/data/semesters-252627.json");

    @Override
    public void load() {
        loadSessionProvider();
    }

    private void loadSessionProvider() {
        ObjectMapper objectMapper = CustomJsonProvider.getMapper();
        // SessionMapper sessionMapper = this.resourceLocator.resolve(SessionMapper.class);
        SchoolSessionProvider schoolSessionProvider =
                new JsonSchoolSessionProvider(SEMESTER_DATA_FILE_PATH, null, objectMapper);

        this.resourceLocator.register(SchoolSessionProvider.class, schoolSessionProvider);
    }
}
