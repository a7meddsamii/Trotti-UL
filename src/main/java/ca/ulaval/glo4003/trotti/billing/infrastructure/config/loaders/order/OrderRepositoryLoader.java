package ca.ulaval.glo4003.trotti.billing.infrastructure.config.loaders.order;

import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.config.bootstrapper.Bootstrapper;

public class OrderRepositoryLoader extends Bootstrapper {
	
	@Override
	public void load() {
		loadOrderRepository();
	}
	
	private void loadOrderRepository() {
		// TODO : Wait for merge for implementation of OrderRepository
		// OrderRepository orderRepository = new OrderRepository();
		// this.resourceLocator.register(OrderRepository.class, orderRepository);
	}
}
