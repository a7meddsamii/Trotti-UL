package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.AddItemDto;
import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.repository.OrderRepository;
import ca.ulaval.glo4003.trotti.billing.domain.payment.PaymentGateway;

public class OrderApplicationService {
	private final PaymentGateway paymentGateway;
	private final OrderRepository orderRepository;
	
	public OrderApplicationService(PaymentGateway paymentGateway, OrderRepository orderRepository) {
		this.paymentGateway = paymentGateway;
		this.orderRepository = orderRepository;
	}
	
	public OrderDto addItem(AddItemDto)
}
