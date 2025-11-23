package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.session.JsonSchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.session.SessionMapper;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;

public class OrderProviderLoader extends Bootstrapper {
	private static final Path JSON_SCHOOL_SESSION_PROVIDER_RESOURCE_PATH = Path.of("/app/data/semesters-252627.json");
	
	@Override
	public void load() {
		loadSchoolSessionProvider();
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
