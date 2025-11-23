package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.session.JsonSchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.session.SessionMapper;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.config.json.CustomJsonProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;

public class OrderMapperLoader extends Bootstrapper {

    private static final Path JSON_SCHOOL_SESSION_PROVIDER_RESOURCE_PATH = Path.of("/app/data/semesters-252627.json");

    @Override
	public void load() {
		loadSessionMapper();
		loadObjectMapper();
        loadSchoolSessionProvider();
		loadOrderApiMapper();
	}

	private void loadSessionMapper() {
		SessionMapper sessionMapper = new SessionMapper();
		this.resourceLocator.register(SessionMapper.class, sessionMapper);
	}
	
	private void loadObjectMapper() {
		ObjectMapper objectMapper =  CustomJsonProvider.getMapper();
		this.resourceLocator.register(ObjectMapper.class, objectMapper);
	}
	
	private void loadOrderApiMapper() {
		SchoolSessionProvider schoolSessionProvider = this.resourceLocator.resolve(SchoolSessionProvider.class);
		OrderApiMapper orderApiMapper = new OrderApiMapper(schoolSessionProvider);
		this.resourceLocator.register(OrderApiMapper.class, orderApiMapper);
	}

    private void loadSchoolSessionProvider() {
        SessionMapper sessionMapper = this.resourceLocator.resolve(SessionMapper.class);
        ObjectMapper objectMapper = this.resourceLocator.resolve(ObjectMapper.class);
        SchoolSessionProvider schoolSessionProvider = new JsonSchoolSessionProvider(
                JSON_SCHOOL_SESSION_PROVIDER_RESOURCE_PATH, sessionMapper, objectMapper
        );
        this.resourceLocator.register(SchoolSessionProvider.class, schoolSessionProvider);
    }
}
