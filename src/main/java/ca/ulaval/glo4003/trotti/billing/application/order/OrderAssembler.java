package ca.ulaval.glo4003.trotti.billing.application.order;

import ca.ulaval.glo4003.trotti.billing.application.order.dto.OrderDto;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.Order;
import ca.ulaval.glo4003.trotti.billing.domain.order.entities.OrderItem;
import ca.ulaval.glo4003.trotti.commons.domain.events.order.OrderItemSnapshot;
import ca.ulaval.glo4003.trotti.order.domain.events.OrderPlacedEvent;

import java.util.List;

public class OrderAssembler {
	public OrderDto assemble(Order order) {
		return new OrderDto();
	}
	
	public OrderPlacedEvent assembleOrderPlacedEvent(Order order, boolean success) {
		List<OrderItemSnapshot> orderItemSnapshots = order.getItems().stream()
				.map(this::toOrderItemSnapshot)
				.toList();
		
//		OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(
//				order.g(),
//				orderItemSnapshots,
//				order.getOrderId(),
//				order.getTotalCost(),
//				
//		);
		
		return null;
	}
	
	private OrderItemSnapshot toOrderItemSnapshot(OrderItem orderItem) {
		return new OrderItemSnapshot(
				orderItem.getSession(),
				orderItem.getMaximumTravelingTime().getValue(),
				orderItem.getBillingFrequency()
		);
	}
}
