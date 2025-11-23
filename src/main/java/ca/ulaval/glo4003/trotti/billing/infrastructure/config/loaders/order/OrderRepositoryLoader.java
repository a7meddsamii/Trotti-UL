package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.billing.infrastructure.order.repository.InMemoryOrderRepository;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class OrderRepositoryLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadOrderRepository();
	}
	
	private void loadOrderRepository() {
		 OrderRepository orderRepository = new InMemoryOrderRepository();
		 this.resourceLocator.register(OrderRepository.class, orderRepository);
	}
}
