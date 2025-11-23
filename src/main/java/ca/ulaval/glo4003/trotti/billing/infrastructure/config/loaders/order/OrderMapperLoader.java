package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.api.order.mapper.OrderApiMapper;
import ca.ulaval.glo4003.trotti.billing.domain.order.provider.SchoolSessionProvider;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.session.SessionMapper;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;
import ca.ulaval.glo4003.trotti.config.json.CustomJsonProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderMapperLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadSessionMapper();
		loadObjectMapper();
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
}
